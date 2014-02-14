public class Note {
    private NoteValue value;
    private Pitch pitch;

    public Note(NoteValue value, Pitch pitch) {
        if (value == null)
            throw new IllegalArgumentException("value may not be null");

        this.value = value;
        this.pitch = pitch;
    }

    public boolean isRest() {
        return this.pitch == null;
    }

    public Pitch pitch() {
        return this.pitch;
    }

    public NoteValue value() {
        return this.value;
    }
    
    // Given a time in seconds for how long the note has been playing
    // (sec) together with a Waveform, return a sample of the wave at
    // that time.
    public double at(double sec, Waveform w) {
        double freq = pitch().frequency();
        
        
        
        return 0;
    }
}
