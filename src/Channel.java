public class Channel {
    private Waveform waveform;
    private Note[] notes;

    public Channel(Waveform waveform, Note[] notes) {
        if (waveform == null)
            throw new IllegalArgumentException("waveform may not be null");

        if (notes == null)
            throw new IllegalArgumentException("notes may not be null");

        this.waveform = waveform;
        this.notes = notes;
    }
    
    public Note[] notes() {
        return notes;
    }
    
    public Waveform waveform() {
        return waveform;
    }
}
