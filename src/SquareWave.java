public class SquareWave extends Waveform {
    public SquareWave() { }

    protected double _at(double t) {
        return t > 0.5 ? 1 : -1;
    }
}
