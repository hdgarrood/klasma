public class TriangleWave implements Waveform {
    public TriangleWave() { }

    public double at(double t) {
        return 1 + (2 * (- Math.abs(t - 0.5)));
    }
}
