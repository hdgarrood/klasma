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
    
    // 1 over the sample rate (samples / sec)
    private double sampleDelta;
    
    public ChannelReader(Channel channel, double tempo, double sampleRate) {
        if (channel == null)
            throw new IllegalArgumentException("channel may not be null");
        
        this.channel = channel;
        this.currentNoteIndex = 0;
        this.currentNotePlayTime = 0;
        this.tempo = tempo;
        this.currentNoteLength = getCurrentNoteLength();
        this.sampleDelta = 1.0 / sampleRate;
    }
    
    private double getCurrentNoteLength() {
        return getCurrentNote().value().length() * this.tempo;
    }
    
    private Note getCurrentNote() {
        return this.channel.notes()[this.currentNoteIndex];
    }
    
    public boolean hasMore() {
        return !atEnd();
    }

    private boolean atEnd() {
        return this.channel.notes().length <= this.currentNoteIndex;
    }
    
    // 
    public double getNext() {
        advance();
        return getCurrentAmplitude();
    }
    
    // Try to move to the next sample position. Returns true if we
    // haven't yet reached the end, false otherwise.
    private void advance() {
        double newPlayTime = this.currentNotePlayTime + this.sampleDelta;
        
        if (this.currentNoteLength > newPlayTime) {
            this.currentNotePlayTime = newPlayTime;
        } else {
            try {
                moveToNextNote();
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new EndOfChannelException(ex);
            }
        }
    }
    
    private double getCurrentAmplitude() {
        double freq = getCurrentNote().pitch().frequency();
        double t = this.currentNotePlayTime * freq;
        return this.channel.waveform().at(t);
    }

    // Try to move along one note. Return true if successful. Returns
    // false otherwise (ie if it's at the end)
    private void moveToNextNote() {
        this.currentNoteIndex++;
        this.currentNotePlayTime = 0;
        this.currentNoteLength = getCurrentNoteLength();
        
    }
}