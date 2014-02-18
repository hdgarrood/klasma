public class WaveformParser {
    public static Waveform parse(String str) {
        switch (str) {
        case "TRIANGLE":
            return new TriangleWave();
        case "SQUARE":
            return new SquareWave();
        case "SAWTOOTH":
            return new SawtoothWave();
        case "NOISE":
            return new Noise();
        default:
            throw new TrackException(String.format(
                        "Unknown waveform '%s'. This shouldn't happen.", str));
        }
    }
}
