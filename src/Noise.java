public class Noise implements Waveform {
    public Noise() { }

    public double at(double t) {
        return (2 * Math.random()) - 1;
    }
}
