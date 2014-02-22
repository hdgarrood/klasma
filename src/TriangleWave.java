public class TriangleWave extends Waveform {
    public TriangleWave() { }

    protected double _at(double t) {
        return 1 + (4 * (- Math.abs(t - 0.5)));
    }
}
