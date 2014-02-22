import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {
    private static String VERSION = "0.1.0";

    private static void p(Object message) {
        System.out.println(message);
    }

    // We want to be able to:
    // * Read from either stdin or a file
    // * Write WAV to either stdout or a file, or play the track.
    private static void showHelp() {
        p("usage: klasma [-i INPUT-FILE] (-o OUTPUT-FILE|--stdout|--play)");
        p("options:");
        p("  -i INPUT-FILE:    Specify a file to use as input.");
        p("                    Default: read from standard input.");
        p("");
        p("  -o OUTPUT-FILE:   Specify a the name of a WAV file to write.");
        p("");
        p("  --stdout:         Write WAV data to standard output.");
        p("");
        p("  --play:           Play the track.");
    }

    private static void parseArgs() throws IOException {
        Track track = null;
        try {
            track = TrackBuilder.build(System.in);
        } catch (TrackException e) {
            System.out.println(e.toString());
            return;
        }
        AudioOutput.play(track);
    }

    public static void main(String[] args) throws IOException {
        // Get arguments from command line.
        String inputFileName  = getArg("-i", args);
        String outputFileName = getArg("-o", args);
        boolean hasFileName   = outputFileName != null;
        boolean toStdOut      = hasArg("--stdout", args);
        boolean play          = hasArg("--play", args);
        boolean help          = hasArg("-h", args) || hasArg("--help", args);

        List<String> extraArgs = getExtraArgs(args);
        if (!extraArgs.isEmpty())
            errExtraArgs(extraArgs);

        if (countTrue(hasFileName, toStdOut, play) != 1)
            err("must specify exactly one of: " +
                    "-o OUTPUT-FILE, --stdout, --play.");

        if (help) {
            showHelp();
            return;
        }

        // Parse the track.
        InputStream inputStream = inputFileName != null ?
            new FileInputStream(inputFileName) :
            System.in;
        Track track = null;

        try {
            track = TrackBuilder.build(inputStream);
        } catch (TrackException e) {
            dieFromTrackException(e);
        }

        // Output the track.
        if (hasFileName) {
            AudioOutput.writeWaveFile(track,
                    new FileOutputStream(outputFileName));
        } else if (toStdOut) {
            AudioOutput.writeWaveFile(track, System.out);
        } else {
            AudioOutput.play(track);
        }
    }

    // Get a value associated with an option in a list of arguments.
    // If found, sets both the option name and value to null.
    private static String getArg(String argName, String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null
                 && args[i].equals(argName)
                 && args.length >= i+1) {
                String value = args[i + 1];
                args[i] = null;
                args[i + 1] = null;
                return value;
            }
        }

        return null;
    }

    // Does an array of arguments contain a given value?
    // If the value is found, sets it to null.
    private static boolean hasArg(String argName, String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null && args[i].equals(argName)) {
                args[i] = null;
                return true;
            }
        }

        return false;
    }

    // Return all elements which are not null.
    private static List<String> getExtraArgs(String[] args) {
        List<String> retval = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null)
                retval.add(args[i]);
        }
        return retval;
    }

    private static void errExtraArgs(List<String> args) {
        String extraArgs = join(args, ", ");
        err("extra arguments: " + extraArgs);
    }

    private static String join(List<String> l, String glue) {
        int size = l.size();
        if (l.isEmpty())
            return "";

        StringBuilder out = new StringBuilder();
        out.append(l.get(0));
        for (int x=1; x < size; x++) {
            out.append(glue);
            out.append(l.get(x));
        }

        return out.toString();
    }

    private static void err(String message) {
        System.err.println("klasma: " + message);
        showHelp();
        System.exit(1);
    }

    private static int countTrue(boolean ... values) {
        int total = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i]) total++;
        }
        return total;
    }

    private static void dieFromTrackException(TrackException e) {
        System.err.println("klasma: " + e.getMessage());
        System.exit(1);
    }
}
