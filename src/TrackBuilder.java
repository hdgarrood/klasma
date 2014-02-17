import java.util.ArrayList;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

// Builds a Track from a string.
public class TrackBuilder extends TrackBaseListener {
    private Map<String, Block> blocks;
    private Map<String, Channel> channels;

    private TrackBuilder() { super(); }

    public static Track build(String input) {
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

    private Track toTrack() {
        return null;
    }

    public void enterTrack_decl(TrackParser.Track_declContext ctx) {
        System.out.println(ctx);
    }
}
