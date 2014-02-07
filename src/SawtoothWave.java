public class SawtoothWave implements Waveform {
    public SawtoothWave() { }

    public double at(double t) {
        return (2 * t) - 1;
    }
}
