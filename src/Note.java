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
}
