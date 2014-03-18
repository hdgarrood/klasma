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
    private Map<String, List<Note>> blockDecls = new HashMap<>();
    private List<Pair<String, List<Note>>> channelDecls = new ArrayList<>();

    private String currentWaveformName = null;
    private String currentBlockName = null;

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
        List<Channel> channelList = getValuesAt(channels, channelsInTrack);
        return new Track(channelList);
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

    // When entering a channel declaration:
    //   * Make a note of its waveform name.
    //   * Make a new list of blocks for it.
    public void enterChannel_decl(TrackParser.Channel_declContext ctx) {
        String waveformName = ctx.WAVE().getSymbol().getText();
        this.currentWaveformName = waveformName;
        this.currentBlockList = new ArrayList<>();
    }

    // When entering a block name, add the block with that name to the end
    // of the list of blocks for the current channel.
    public void enterBlock_name(TrackParser.Block_nameContext ctx) {
        String blockName = ctx.ID().getSymbol().getText();
        this.currentBlockList.add(blockName);
    }

    // When exiting a channel declaration:
    //   * Add the channel to the list of channels
    public void exitChannel_decl(TrackParser.Channel_declContext ctx) {
        this.currentWaveformName = null;
        this.currentBlockList = null;
    }

    public void enterBlock_decl(TrackParser.Block_declContext ctx) {
        String blockName = ctx.ID().getSymbol().getText();
        this.currentBlockName = blockName;

        if (blockDecls.containsKey(blockName)) {
            throw new TrackException(String.format(
                        "Multiple declarations of block '%s'", blockName));
        } else {
            this.blockDecls.put(
                    this.currentBlockName, new ArrayList<Note>());
        }
    }

    public void enterNote(TrackParser.NoteContext ctx) {
        String noteName = ctx.NOTENAME().getSymbol().getText();
        int octave = getOctave(ctx);
        String length = getLength(ctx);

        Note note = makeNote(noteName, octave, length);
        this.blockDecls.get(this.currentBlockName).add(note);
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

        if (noteName == null)
            return Note.Rest(noteValue);
        else {
            Pitch pitch = new Pitch(noteName, octave);
            return new Note(noteValue, pitch);
        }
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
        this.currentBlockName = null;
    }
}
