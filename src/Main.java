import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Pitch c = new Pitch(NoteName.C, 4);
        Pitch d = new Pitch(NoteName.D, 4);
        Pitch e = new Pitch(NoteName.E, 4);
        Channel doeADeer = new Channel(new TriangleWave(), new Note[] {
           new Note(NoteValue.Minim.dotted(), c),
           new Note(NoteValue.Crotchet,       d),
           new Note(NoteValue.Minim.dotted(), e),
           new Note(NoteValue.Crotchet,       c),
           new Note(NoteValue.Minim,          e),
           new Note(NoteValue.Minim,          c),
           new Note(NoteValue.Semibreve,      e),
        });
        Channel drums = new Channel(new Noise(), new Note[] {
            new Note(NoteValue.Quaver,  c),
            Note.Rest(NoteValue.Quaver),
            Note.Rest(NoteValue.Minim.dotted()),
            new Note(NoteValue.Quaver,  c),
            Note.Rest(NoteValue.Quaver),
            Note.Rest(NoteValue.Minim.dotted()),
            new Note(NoteValue.Quaver,  c),
            Note.Rest(NoteValue.Quaver),
            Note.Rest(NoteValue.Minim.dotted()),
            new Note(NoteValue.Quaver,  c),
            Note.Rest(NoteValue.Quaver),
            Note.Rest(NoteValue.Minim.dotted())
        });
        Track track = new Track(new Channel[] { doeADeer, drums });
        track.play();
    }
}
