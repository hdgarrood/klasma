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
        double tempo = 1.0/15;
        double sampleRate = 64100;
        ChannelReader rdr = new ChannelReader(doeADeer, tempo, sampleRate);
        
        for (int i = 0; i < 100; i++) {
            System.out.println(rdr.getNext());
        }
    }
}
