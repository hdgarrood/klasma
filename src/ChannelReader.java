public class ChannelReader {
    private Channel channel;
    
    // What note in the Channel have we reached?
    private int currentNoteIndex;
    
    // How long has the current note been playing for (sec)?
    private double currentNotePlayTime;
    
    // Tempo (semibreves / sec)
    private double tempo;
    
    // Sample rate (samples / sec)
    private double sampleRate;
    
    public ChannelReader(Channel channel, double tempo, double sampleRate) {
        if (channel == null)
            throw new IllegalArgumentException("channel may not be null");
        
        this.channel = channel;
        this.currentNoteIndex = 0;
        this.currentNotePlayTime = 0;
        this.tempo = tempo;
        this.sampleRate = sampleRate;
    }
    
    public double getNext() {
        return 0;
    }
}