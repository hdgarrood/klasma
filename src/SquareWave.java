public class SquareWave extends Waveform {
    public SquareWave() { }

    protected double _at(double t) {
        if (t < 0.5) return 1;
        return -1;
    }
}
