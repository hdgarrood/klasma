import java.util.List;

public class Block {
    private List<Note> notes;

    public Block(List<Note> notes) {
        this.notes = notes;
    }

    public int length() {
        return notes.size();
    }
    
    public List<Note> notes() {
        return notes;
    }
}
