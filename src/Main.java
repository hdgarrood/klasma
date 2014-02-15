import java.io.IOException;

public class Main {
    private static void harry() throws IOException {
        Pitch bottomD = new Pitch(NoteName.D, 2);
        Pitch bf = new Pitch(NoteName.BFlat, 3);
        Pitch c = new Pitch(NoteName.C, 4);
        Pitch d = new Pitch(NoteName.D, 4);
        Pitch e = new Pitch(NoteName.E, 4);
        Pitch f = new Pitch(NoteName.F, 4);
        
        Channel chanA = new Channel(new SquareWave(), new Note[] {
           new Note(NoteValue.Breve, bottomD),
           new Note(NoteValue.Breve, bottomD),
        });
        
        Channel chanB = new Channel(new SawtoothWave(), new Note[] {
           Note.Rest(NoteValue.Crotchet),
           new Note(NoteValue.Crotchet, d),
           new Note(NoteValue.Crotchet, d),
           new Note(NoteValue.Quaver, bottomD),
           new Note(NoteValue.Quaver, d),
           new Note(NoteValue.Quaver, f),
           new Note(NoteValue.Crotchet, e),
           new Note(NoteValue.Crotchet, d),
           new Note(NoteValue.Quaver, f),
           new Note(NoteValue.Crotchet, e),
           
           Note.Rest(NoteValue.Crotchet),
           new Note(NoteValue.Crotchet, bf),
           new Note(NoteValue.Crotchet, bf),
           Note.Rest(NoteValue.Crotchet),
           new Note(NoteValue.Quaver, c),
           new Note(NoteValue.Crotchet, c),
           new Note(NoteValue.Crotchet, c),
           Note.Rest(NoteValue.Quaver),
           new Note(NoteValue.Crotchet, c),
        });
        
        Channel drums = new Channel(new Noise(), new Note[] {
            new Note(NoteValue.Quaver,  d),
            Note.Rest(NoteValue.Quaver),
            Note.Rest(NoteValue.Minim.dotted()),
            new Note(NoteValue.Quaver,  d),
            Note.Rest(NoteValue.Quaver),
            Note.Rest(NoteValue.Minim.dotted()),
            new Note(NoteValue.Quaver,  d),
            Note.Rest(NoteValue.Quaver),
            Note.Rest(NoteValue.Minim.dotted()),
            new Note(NoteValue.Quaver,  d),
            Note.Rest(NoteValue.Quaver),
            Note.Rest(NoteValue.Minim.dotted())
        });
        Track track = new Track(new Channel[] { chanA, chanB, drums });
        track.play();
        track.play();
        track.play();    
    }
    
    private static void laura() throws IOException {
        Pitch a = new Pitch(NoteName.A, 3);
        Pitch b = new Pitch(NoteName.B, 3);
        Pitch c = new Pitch(NoteName.C, 4);
        Pitch d = new Pitch(NoteName.D, 4);
        Pitch e = new Pitch(NoteName.E, 4);
        Pitch f = new Pitch(NoteName.F, 4);
        Pitch g = new Pitch(NoteName.G, 4);
        
        Channel chanB = new Channel(new SawtoothWave(), new Note[] {
            Note.Rest(NoteValue.Crotchet),
            new Note(NoteValue.Crotchet, e),
            new Note(NoteValue.Crotchet, d),
            new Note(NoteValue.Crotchet, b),
            new Note(NoteValue.Crotchet, b),
            new Note(NoteValue.Crotchet, a),
            new Note(NoteValue.Crotchet, g),
            new Note(NoteValue.Crotchet, e)

        });
        Track track = new Track(new Channel[] { chanB });
        track.play();
    }
    
    public static void main(String[] args) throws IOException {
        laura();
    }
}
