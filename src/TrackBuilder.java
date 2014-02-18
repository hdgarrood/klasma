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
    private List<String> channelsInTrack =
        new ArrayList<String>();
    private Map<String, List<String>> channelDecls =
        new HashMap<String, List<String>>();
    private Map<String, List<Note>> blockDecls =
        new HashMap<String, List<Note>>();

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
        Map<String, Channel> allChannels = getAllDeclaredChannels();
        List<Channel> channels = getChannelsToBeInTrack(allChannels);
        return new Track((Channel[])channels.toArray());
    }

    private Map<String, Channel> getAllDeclaredChannels() {
        Map<String, Channel> channels = new HashMap<String, Channel>();
        Iterator<String> channelDeclIter = channelDecls.keySet().iterator();

        while (channelDeclIter.hasNext()) {
            // The name of a channel declaration appearing in the track.
            String channelName = channelDeclIter.next();
            // The blocks appearing in the channel declaration
            List<String> blockNames = channelDecls.get(channelName);
            // To be populated imminently with blocks referenced in this
            // channel
            List<Block> blocks = new ArrayList<Block>();

            Iterator<String> blockNameIter = blockNames.iterator();
            while (blockNameIter.hasNext()) {
                String blockName = blockNameIter.next();
                if (blockDecls.containsKey(blockName)) {
                    blocks.add(new Block((Note[])blockDecls.get(blockName).toArray()));
                } else {
                    throw new TrackException(String.format(
                        "Unknown block '%s' in declaration of channel '%s'",
                        blockName, channelName));
                }
            }
        }

        return channels;
    }

    private List<Channel> getChannelsToBeInTrack(
            Map<String, Channel> allChannels) {
        Iterator<String> iterator = channelsInTrack.iterator();
        List<Channel> channels = new ArrayList<Channel>();

        while (iterator.hasNext()) {
            String channelName = iterator.next();
            if (allChannels.containsKey(channelName)) {
                channels.add(allChannels.get(channelName));
            } else {
                throw new TrackException(String.format(
                    "Unknown channel '%s' in track declaration",
                    channelName));
            }
        }

        return channels;
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
        double value = 1.0;
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

    private List<Note> getNotes(TrackParser.Block_declContext ctx) {
        return new ArrayList<Note>();
        //return ctx.getTokens(TrackParser.ID);
    }
}
