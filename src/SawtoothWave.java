public class SawtoothWave extends Waveform {
    public SawtoothWave() { }

    protected double _at(double t) {
        return (2 * t) - 1;
    }
}
