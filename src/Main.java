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
        AudioOutput.play(track.toAudioInputStream());    
    }
    
    private static void laura() throws IOException {
        Pitch lowe  = new Pitch(NoteName.E, 3);
        Pitch lowfs  = new Pitch(NoteName.FSharp, 3);
        Pitch lowg  = new Pitch(NoteName.G, 3);
        Pitch a     = new Pitch(NoteName.A, 3);
        Pitch b     = new Pitch(NoteName.B, 3);
        Pitch c     = new Pitch(NoteName.C, 4);
        Pitch d     = new Pitch(NoteName.D, 4);
        Pitch e     = new Pitch(NoteName.E, 4);
        Pitch f     = new Pitch(NoteName.F, 4);
        Pitch g     = new Pitch(NoteName.G, 4);
        
        Channel chanB = new Channel(new SawtoothWave(), new Note[] {
            new Note(NoteValue.Minim, lowe),
            new Note(NoteValue.Crotchet, e),
            new Note(NoteValue.Crotchet, d),
            new Note(NoteValue.Crotchet, b),
            new Note(NoteValue.Crotchet, b),
            new Note(NoteValue.Crotchet, a),
            new Note(NoteValue.Crotchet, lowg),
            new Note(NoteValue.Crotchet, lowe),
            new Note(NoteValue.Crotchet, lowfs),
            new Note(NoteValue.Crotchet, lowg),
            new Note(NoteValue.Minim, d),
            new Note(NoteValue.Minim, b),

        });
        Track track = new Track(new Channel[] { chanB });
        AudioOutput.play(track.toAudioInputStream());
    }
    
    private static void laura2() throws IOException {
        Pitch lowb  = new Pitch(NoteName.B, 2);
        Pitch lowe  = new Pitch(NoteName.E, 3);
        Pitch lowfs = new Pitch(NoteName.FSharp, 3);
        Pitch lowg  = new Pitch(NoteName.G, 3);
        Pitch a     = new Pitch(NoteName.A, 3);
        Pitch b     = new Pitch(NoteName.B, 3);
        Pitch c     = new Pitch(NoteName.C, 4);
        Pitch d     = new Pitch(NoteName.D, 4);
        Pitch e     = new Pitch(NoteName.E, 4);
        Pitch f     = new Pitch(NoteName.F, 4);
        Pitch g     = new Pitch(NoteName.G, 4);
        Pitch highb = new Pitch(NoteName.B, 5);
        
        Channel chanA = new Channel(new SquareWave(), new Note[] {
            Note.Rest(NoteValue.Minim),
            Note.Rest(NoteValue.Semibreve),
            Note.Rest(NoteValue.Semibreve),
            Note.Rest(NoteValue.Minim),
            Note.Rest(NoteValue.Semibreve),
            Note.Rest(NoteValue.Semibreve),
            Note.Rest(NoteValue.Minim),
            
            new Note(NoteValue.Minim.dotted(), b),
            new Note(NoteValue.Semibreve.dotted(),d),
            Note.Rest(NoteValue.Minim.dotted()),
            
            new Note(NoteValue.Minim.dotted(), b),
            new Note(NoteValue.Semibreve.dotted(),e),
            Note.Rest(NoteValue.Minim.dotted()),
            
            new Note(NoteValue.Minim.dotted(), b),
            new Note(NoteValue.Semibreve.dotted(),d),
            Note.Rest(NoteValue.Minim.dotted()),
            
            new Note(NoteValue.Minim.dotted(), b),
            new Note(NoteValue.Semibreve.dotted(),e),
            Note.Rest(NoteValue.Minim.dotted()),
           
            new Note(NoteValue.Minim, b),
            new Note(NoteValue.Semibreve,a),
            new Note(NoteValue.Semibreve,lowg),
            Note.Rest(NoteValue.Minim),
            
            new Note(NoteValue.Minim, b),
            new Note(NoteValue.Semibreve,a),
            new Note(NoteValue.Semibreve,lowg),
            Note.Rest(NoteValue.Minim),
           
            new Note(NoteValue.Minim,a),
            new Note(NoteValue.Breve, b),
            
            new Note(NoteValue.Breve, lowe),
        });
            
         Channel chanB = new Channel(new SawtoothWave(), new Note[] {
             new Note(NoteValue.Minim,lowfs),
             new Note(NoteValue.Semibreve, lowb),
             new Note(NoteValue.Semibreve, b),
             Note.Rest(NoteValue.Minim),
             //12
             
             new Note(NoteValue.Minim,lowfs),
             new Note(NoteValue.Semibreve, lowb),
             new Note(NoteValue.Semibreve, b),
             Note.Rest(NoteValue.Minim),
             
             new Note(NoteValue.Minim,lowfs),
             new Note(NoteValue.Semibreve, lowb),
             new Note(NoteValue.Semibreve, b),
             Note.Rest(NoteValue.Minim),
             
             new Note(NoteValue.Minim,lowfs),
             new Note(NoteValue.Semibreve, lowb),
             new Note(NoteValue.Semibreve, b),
             Note.Rest(NoteValue.Minim),
            
             new Note(NoteValue.Minim,lowfs),
             new Note(NoteValue.Semibreve, lowb),
             new Note(NoteValue.Semibreve, b),
             Note.Rest(NoteValue.Minim),
             //12
             
             new Note(NoteValue.Minim,lowfs),
             new Note(NoteValue.Semibreve, lowb),
             new Note(NoteValue.Semibreve, b),
             Note.Rest(NoteValue.Minim),
             
             new Note(NoteValue.Minim,lowfs),
             new Note(NoteValue.Semibreve, lowb),
             new Note(NoteValue.Semibreve, b),
             Note.Rest(NoteValue.Minim),
             
             new Note(NoteValue.Minim,lowfs),
             new Note(NoteValue.Semibreve, lowb),
             new Note(NoteValue.Semibreve, b),
             Note.Rest(NoteValue.Minim),
            
            
         });
        
        Track track = new Track(new Channel[] { chanA, chanB });
        AudioOutput.play(track.toAudioInputStream());
    }
    
    public static void main(String[] args) throws IOException {
        laura2();
    }
}
