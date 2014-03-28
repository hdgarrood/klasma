public class SquareWave extends Waveform {
    public SquareWave() { }

    private double VOLUME = 0.577;

    protected double _at(double t) {
        return VOLUME * (t > 0.5 ? 1 : -1);
    }
}
