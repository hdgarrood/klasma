public class AdsrEnvelope implements Envelope {
    public AdsrEnvelope() { }

    public double at(double t) {
        if (t < 0.25) {
            return attack(t);
        } else if (t < 0.5) {
            return decay(t);
        } else if (t < 0.75) {
            return sustain(t);
        } else {
            return release(t);
        }
    }

    private double attack(double t) {
        return t * 4;
    }

    private double decay(double t) {
        return (-2 * t) + 1.5;
    }

    private double sustain(double t) {
        return 0.5;
    }

    private double release(double t) {
        return (-2 * t) + 2;
    }
}
