import java.io.IOException;
import java.io.FileInputStream;

public class Main {
    private static void parse() throws IOException {
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
        parse();
    }
}
