import java.io.IOException;
import java.io.FileInputStream;

public class Main {
    // private static void laura() throws IOException {
    //     Pitch lowe  = new Pitch(NoteName.E, 3);
    //     Pitch lowfs  = new Pitch(NoteName.FSharp, 3);
    //     Pitch lowg  = new Pitch(NoteName.G, 3);
    //     Pitch a     = new Pitch(NoteName.A, 3);
    //     Pitch b     = new Pitch(NoteName.B, 3);
    //     Pitch c     = new Pitch(NoteName.C, 4);
    //     Pitch d     = new Pitch(NoteName.D, 4);
    //     Pitch e     = new Pitch(NoteName.E, 4);
    //     Pitch f     = new Pitch(NoteName.F, 4);
    //     Pitch g     = new Pitch(NoteName.G, 4);
        
    //     Channel chanB = new Channel(new SawtoothWave(), new Note[] {
    //         new Note(NoteValue.Minim, lowe),
    //         new Note(NoteValue.Crotchet, e),
    //         new Note(NoteValue.Crotchet, d),
    //         new Note(NoteValue.Crotchet, b),
    //         new Note(NoteValue.Crotchet, b),
    //         new Note(NoteValue.Crotchet, a),
    //         new Note(NoteValue.Crotchet, lowg),
    //         new Note(NoteValue.Crotchet, lowe),
    //         new Note(NoteValue.Crotchet, lowfs),
    //         new Note(NoteValue.Crotchet, lowg),
    //         new Note(NoteValue.Minim, d),
    //         new Note(NoteValue.Minim, b),

    //     });
    //     Track track = new Track(new Channel[] { chanB });
    //     AudioOutput.play(track.toAudioInputStream());
    // }
    
    private static void fuckYouEclipse() throws IOException {
        System.setIn(new FileInputStream("example.txt"));
        parse();
    }

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
