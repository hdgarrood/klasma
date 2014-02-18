import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class Channel {
    private Waveform waveform;
    private List<Block> blocks;

    public Channel(Waveform waveform, List<Block> blocks) {
        if (waveform == null)
            throw new IllegalArgumentException("waveform may not be null");

        if (blocks == null)
            throw new IllegalArgumentException("blocks may not be null");

        this.waveform = waveform;
        this.blocks = blocks;
    }

    public List<Block> blocks() {
        return blocks;
    }
    
    public List<Note> notes() {
        List<Note> notes = new ArrayList<Note>();

        Iterator<Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            Block b = iterator.next();
            notes.addAll(b.notes());
        }

        return notes;
    }
    
    public Waveform waveform() {
        return waveform;
    }
    
    public ChannelReader getReader(double tempo, double sampleRate) {
        return new ChannelReader(this, tempo, sampleRate);
    }
}
