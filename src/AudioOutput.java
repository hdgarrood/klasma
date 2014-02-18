import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioOutput {    
    public static void writeWaveFile(AudioInputStream stream, String filename)
            throws IOException {
        OutputStream out = new FileOutputStream(filename);
        AudioSystem.write(stream, AudioFileFormat.Type.WAVE, out);
    }
    
    private static int BUFFER_SIZE = 1024;
    
    public static void play(AudioInputStream stream)
            throws IOException {
        SourceDataLine line = null;
        DataLine.Info  info = new DataLine.Info(
                SourceDataLine.class,
                Track.getAudioFormat());
        try
        {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(Track.getAudioFormat());
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

    public static void play(Track track) throws IOException {
        play(track.toAudioInputStream());
    }
}
