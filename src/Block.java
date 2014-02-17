public class Block {
    private Note[] notes;

    public Block(Note[] notes) {
        this.notes = notes;
    }

    public int length() {
        return notes.length;
    }
    
    public Note[] notes() {
        return notes;
    }
}
