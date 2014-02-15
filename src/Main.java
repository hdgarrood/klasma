import javax.sound.sampled.AudioInputStream;

public class Main {
    public static void main(String[] args) {
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
        Track track = new Track(doeADeer);
        AudioInputStream stream = track.toAudioInputStream();
    }
}
