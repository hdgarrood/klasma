import java.io.ByteArrayInputStream;
import javax.sound.sampled.*;

public class ChannelAudioInputStream extends AudioInputStream {
    private ChannelReader channelReader;
    private byte[] data; 
            
    public ChannelAudioInputStream(ChannelReader reader) {
        this.channelReader = reader;
        super(new ByteArrayInputStream(new byte[0]
                )
    }
}