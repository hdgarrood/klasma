import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class Channel {
    private Waveform waveform;
    private Envelope envelope;
    private List<Block> blocks;

    public Channel(Waveform waveform, List<Block> blocks) {
        if (waveform == null)
            throw new IllegalArgumentException("waveform may not be null");

        if (blocks == null)
            throw new IllegalArgumentException("blocks may not be null");

        this.waveform = waveform;
        this.envelope = getEnvelopeFor(waveform);
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

    public Envelope envelope() {
        return envelope;
    }

    public ChannelReader getReader(double tempo, double sampleRate) {
        return new ChannelReader(this, tempo, sampleRate);
    }

    private Envelope getEnvelopeFor(Waveform waveform) {
        return waveform instanceof Noise ?
            new AdsrEnvelope() : new NullEnvelope();
    }
}
