import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Track {
    private Channel[] channels;
    private static double tempo = 1;
    private static double sampleRate = 64100;
    private AudioFormat audioFormat;
            
    private static int BUFFER_SIZE = 256;
    
    public Track(Channel[] channels) {
        this.channels = channels;
        this.audioFormat = makeAudioFormat();
    }
    
    private AudioFormat makeAudioFormat() {
        return new AudioFormat((float)sampleRate, 8, 1, true, false);
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
                this.audioFormat,
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
    
    public void play() throws IOException {
        AudioInputStream stream = toAudioInputStream();
        
        SourceDataLine line = null;
        DataLine.Info  info = new DataLine.Info(
                SourceDataLine.class,
                this.audioFormat);
        try
        {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
        }
        catch (LineUnavailableException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        line.start();

        byte[] data = new byte[BUFFER_SIZE];
        int totalToRead = stream.available();
        int totalRead = 0;
        while (totalRead < totalToRead)
        {
            int read = stream.read(data, 0, BUFFER_SIZE);
            if (read == -1) break;
            totalRead += read;
            line.write(data, 0, read);
        }

        line.drain();
        line.stop();
        line.close();
        stream.close();
    }
}