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
    
    // Return the note at a specific relative time (ie 1 = a semibreve)
    // together with the 
    private Pair<Note, Double> getNoteAt(double t) {
        Consumer<Note> c = new Consumer<Note>(this.notes);
        
        while (t > 0) {
            Note note = c.next();
            double diff = note.value().length() - t;
            if (diff > 0) {
                return new Pair<Note, Double>(note, diff);
            } else {
                t -= note.value().length();
            }
        }
        return null;
    }

    public double at(double t) {
        Pair<Note, Double> p = getNoteAt(t);
        return p.fst().at(t, this.waveform);
    }
}
