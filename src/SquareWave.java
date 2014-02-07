public class SquareWave implements Waveform {
    public SquareWave() { }

    public double at(double t) {
        if (t < 0.5) return 1;
        return -1;
    }
}
