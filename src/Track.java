import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class Track {
    private static double tempo = 1;
    private static double sampleRate = 64100;
    private static AudioFormat audioFormat =
            new AudioFormat((float)sampleRate, 8, 1, true, false);
    
    private Channel[] channels;
    
    public Track(Channel[] channels) {
        this.channels = channels;
    }
    
    public static AudioFormat getAudioFormat() {
        return audioFormat;
    }
    
    public AudioInputStream toAudioInputStream() {
        ArrayList<Byte> data = new ArrayList<Byte>();
        ChannelReader[] readers = getChannelReaders();
        
        // Avoid clipping
        double multiplier = 127.0 / readers.length;
        
        while (anyReaderHasMore(readers)) {
            double next = 0;
            for (int i = 0; i < readers.length; i++) {
                if (readers[i].hasMore())
                    next += readers[i].getNext();
            }
            data.add((byte)(next * multiplier));
        }
        
        return new AudioInputStream(
                new ByteArrayInputStream(getArray(data)),
                audioFormat,
                data.size());
    }
    
    private ChannelReader[] getChannelReaders() {
        int channels = this.channels.length;
        ChannelReader[] readers = new ChannelReader[channels];
        
        for (int i = 0; i < channels; i++) {
            readers[i] = this.channels[i].getReader(tempo, sampleRate);
        }
        
        return readers;
    }
    
    private boolean anyReaderHasMore(ChannelReader[] readers) {
        int len = readers.length;
        
        for (int i = 0; i < len; i++) {
            if (readers[i].hasMore())
                return true;
        }
        
        return false;
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