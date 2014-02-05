public class Note {
    private NoteValue noteValue;
    private Pitch pitch;

    public Note(NoteValue noteValue, Pitch pitch) {
        if (noteValue == null)
            throw new IllegalArgumentException("noteValue may not be null");

        this.noteValue = noteValue;
        this.pitch     = pitch;
    }

    public boolean isRest() {
        return this.pitch == null;
    }

    public Pitch pitch() {
        return this.pitch;
    }

    public NoteValue noteValue() {
        return this.noteValue;
    }
}
