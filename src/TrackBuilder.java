import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

// Builds a Track from a string.
public class TrackBuilder extends TrackBaseListener {
    // channelsInTrack and channelDecls are List<String> rather than the actual
    // types they represent because we might not have reached the part of the
    // tree that contains the channel or block they reference yet; however
    // blocks do contain all the info necessary to make a Note, so they are
    // stored as a Map<String, Block>
    private List<String> channelsInTrack           = new ArrayList<>();
    private Map<String, List<String>> channelDecls = new HashMap<>();
    private Map<String, List<Note>> blockDecls     = new HashMap<>();
    private Map<String, Waveform> channelWaveforms = new HashMap<>();

    private String currentChannel = null;
    private String currentBlock = null;

    private TrackBuilder() { super(); }

    public static Track build(InputStream input) throws IOException {
        // oh what a wonderful API
        ANTLRInputStream inputStream = new ANTLRInputStream(input);
        TrackLexer lexer = new TrackLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TrackParser parser = new TrackParser(tokens);
        ParserRuleContext tree = parser.track();

        ParseTreeWalker walker = new ParseTreeWalker();
        TrackBuilder builder = new TrackBuilder();
        walker.walk(builder, tree);

        return builder.toTrack();
    }

    public Track toTrack() {
        Map<String, Block> blocks     = constructBlocks();
        Map<String, Channel> channels = constructChannels(blocks);
        List<Channel> filteredChannels =
            getWhereKeyIn(channels, channelsInTrack);
        return new Track(filteredChannels);
    }

    private Map<String, Block> constructBlocks() {
        Map<String, Block> retval = new HashMap<>();

        Iterator<String> iter = blockDecls.keySet().iterator();
        while (iter.hasNext()) {
            String blockName = iter.next();
            List<Note> notes = blockDecls.get(blockName);
            retval.put(blockName, new Block(notes));
        }

        return retval;
    }

    private Map<String, Channel> constructChannels(
            Map<String, Block> blockMap) {
        Map<String, Channel> retval = new HashMap<>();

        Iterator<String> iter = channelDecls.keySet().iterator();
        while (iter.hasNext()) {
            String channelName = iter.next();
            List<String> blockNames = channelDecls.get(channelName);
            List<Block> blocks =
                convertEachToBlock(blockNames, blockMap, channelName);
            Waveform wf = channelWaveforms.get(channelName);
            retval.put(channelName, new Channel(wf, blocks));
        }

        return retval;
    }

    // Convert a list of strings (block names) to a list of blocks.
    // Arguments:
    //   blockNames:  names of blocks to convert
    //   blockMap:    where to look for blocks
    //   channelName: the channel where these blocks occur. Used for error
    //                messages.
    private List<Block> convertEachToBlock(
            List<String> blockNames,
            Map<String, Block> blockMap,
            String channelName) {
        List<Block> retval = new ArrayList<>();

        Iterator<String> iter = blockNames.iterator();
        while (iter.hasNext()) {
            String blockName = iter.next();
            Block block = blockMap.get(blockName);

            if (block == null)
                throw new TrackException(String.format(
                    "Unknown block '%s' in declaration for channel '%s'",
                    blockName, channelName));
            else
                retval.add(block);
        }
        return retval;
    }

    private List<Channel> getWhereKeyIn(
            Map<String, Channel> map,
            List<String> whitelist) {
        List<Channel> retval = new ArrayList<>();

        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if (whitelist.contains(key))
                retval.add(map.get(key));
        }

        return retval;
    }

    // Inside the track declaration
    public void enterChannel_name(TrackParser.Channel_nameContext ctx) {
        String channelName = ctx.ID().getSymbol().getText();
        this.channelsInTrack.add(channelName);
    }

    public void enterChannel_decl(TrackParser.Channel_declContext ctx) {
        String channelName = ctx.ID().getSymbol().getText();
        this.currentChannel = channelName;

        if (channelDecls.containsKey(channelName)) {
            throw new TrackException(String.format(
                        "Multiple declarations of channel '%s'", channelName));
        } else {
            this.channelDecls.put(
                    this.currentChannel, new ArrayList<String>());
        }

        String waveformName = ctx.WAVE().getSymbol().getText();
        Waveform waveform = WaveformParser.parse(waveformName);
        this.channelWaveforms.put(channelName, waveform);
    }

    public void enterBlock_name(TrackParser.Block_nameContext ctx) {
        String blockName = ctx.ID().getSymbol().getText();
        this.channelDecls.get(this.currentChannel).add(blockName);
    }

    public void exitChannel_decl(TrackParser.Channel_declContext ctx) {
        this.currentChannel = null;
    }

    public void enterBlock_decl(TrackParser.Block_declContext ctx) {
        String blockName = ctx.ID().getSymbol().getText();
        this.currentBlock = blockName;

        if (blockDecls.containsKey(blockName)) {
            throw new TrackException(String.format(
                        "Multiple declarations of block '%s'", blockName));
        } else {
            this.blockDecls.put(
                    this.currentBlock, new ArrayList<Note>());
        }
    }

    public void enterNote(TrackParser.NoteContext ctx) {
        String noteName = ctx.NOTENAME().getSymbol().getText();
        int octave = getOctave(ctx);
        String length = getLength(ctx);

        Note note = makeNote(noteName, octave, length);
        this.blockDecls.get(this.currentBlock).add(note);
    }

    private int getOctave(TrackParser.NoteContext ctx) {
        String octave = getTextWithDefault(ctx.OCTAVE(), "4");
        return Integer.parseInt(octave);
    }

    private String getLength(TrackParser.NoteContext ctx) {
        return getTextWithDefault(ctx.LENGTH(), "");
    }

    private String getTextWithDefault(TerminalNode node, String def) {
        if (node == null)
            return def;
        else
            return node.getSymbol().getText();
    }

    private Note makeNote(String noteNameStr, int octave, String length) {
        NoteValue noteValue = lengthToValue(length);
        NoteName noteName = NoteName.parse(noteNameStr);
        Pitch pitch = noteName == null ? null : new Pitch(noteName, octave);

        return new Note(noteValue, pitch);
    }

    private NoteValue lengthToValue(String str) {
        // Default note length is crotchet
        double value = 1.0/4;
        for (int i = 0; i < str.length(); i++) {
            char next = str.charAt(i);
            switch (next) {
            case '+':
                value *= 2;
                break;
            case '-':
                value /= 2;
                break;
            case '.':
                value *= 1.5;
                break;
            default:
                break;
            }
        }
        return new NoteValue(value);
    }

    public void exitBlock_decl(TrackParser.Block_declContext ctx) {
        this.currentBlock = null;
    }
}
