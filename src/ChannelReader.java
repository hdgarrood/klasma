public class ChannelReader {
    private Channel channel;
    
    // What note in the Channel have we reached?
    private int currentNoteIndex;
    
    // How long has the current note been playing for (sec)?
    private double currentNotePlayTime;
    
    // The length of the current note (sec)
    private double currentNoteLength;
    
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
        advance();
    }
    
    private void advance() {
        double newPlayTime = this.currentNotePlayTime + this.sampleRate;
        
        if (this.currentNoteLength > newPlayTime) {
            this.currentNotePlayTime = newPlayTime;
        } else {
            moveToNextNote();
        }
    }

    private void moveToNextNote() {
        this.currentNotePlayTime =
                this.currentNoteLength - this.currentNotePlayTime;
        this.currentNoteIndex++;
        this.currentNoteLength =
                getCurrentNote().value().length() * this.tempo;
    }
    
    private Note getCurrentNote() {
        return this.channel.notes()[this.currentNoteIndex];
    }
}