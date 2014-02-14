public class Noise extends Waveform {
    public Noise() { }

    protected double _at(double t) {
        return (2 * Math.random()) - 1;
    }
}
