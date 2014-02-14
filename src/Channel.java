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
    // together with the amount of relative time that note has been playing
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
        
        throw new IllegalArgumentException("t too large: no more notes");
    }

    // Given a tempo (semibreves / second) and a time (seconds) return a
    // sample of the amplitude at that time.
    public double amplitudeAt(double sec, double tempo) {
        double t = sec * tempo;
        
        Pair<Note, Double> p = getNoteAt(t);
        return p.fst().at(p.snd() / tempo, this.waveform);
    }
}
