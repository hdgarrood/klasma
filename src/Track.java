import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class Track {
    private Channel channel;
    private static double tempo = 1.0 / 15;
    private static double sampleRate = 64100;
    
    public Track(Channel channel) {
        this.channel = channel;
    }
    
    public AudioInputStream toAudioInputStream() {
        ArrayList<Byte> data = new ArrayList<Byte>();
        ChannelReader reader = this.channel.getReader(tempo, sampleRate);
        
        while (reader.hasMore()) {
            double next = reader.getNext();
            data.add((byte)(next * 127));
        }
        
        return new AudioInputStream(
                new ByteArrayInputStream(getArray(data)),
                new AudioFormat((float)sampleRate, 8, 1, true, false),
                data.size());
    }
    
    private byte[] getArray(ArrayList<Byte> data) {
        int size = data.size();
        byte[] array = new byte[size];
        
        for (int i = 0; i < size; i++) {
            array[i] = data.get(i);
        }
        
        return array;
    }
}