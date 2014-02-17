public class Channel {
    private Waveform waveform;
    private Block[] blocks;

    public Channel(Waveform waveform, Block[] blocks) {
        if (waveform == null)
            throw new IllegalArgumentException("waveform may not be null");

        if (blocks == null)
            throw new IllegalArgumentException("blocks may not be null");

        this.waveform = waveform;
        this.blocks = blocks;
    }

    public Block[] blocks() {
        return blocks;
    }
    
    public Note[] notes() {
        int totalLength = 0;

        for (int i = 0; i < blocks.length; i++) {
            totalLength += blocks[i].length();
        }

        Note[] notes = new Note[totalLength];
        int currentIndex = 0;

        for (int i = 0; i < blocks.length; i++) {
            Note[] blockNotes = blocks[i].notes();

            for (int j = 0; j < blockNotes.length; j++) {
                notes[currentIndex] = blockNotes[j];
                currentIndex++;
            }
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
