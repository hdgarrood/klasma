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
    private Channel channel;
    private static double tempo = 1;
    private static double sampleRate = 64100;
    
    private AudioFormat audioFormat =
            new AudioFormat((float)sampleRate, 8, 1, true, false);
    
    private static int BUFFER_SIZE = 256;
    
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
                this.audioFormat,
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