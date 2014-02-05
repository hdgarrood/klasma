public class Pitch {
    private static final int OCTAVE_MIN           = 0;
    private static final int OCTAVE_MAX           = 10;
    private static final int SEMITONES_PER_OCTAVE = 12;
    private static final double A4_FREQUENCY      = 440.0;

    private static final double SEMITONE_RATIO
        = Math.pow(2.0, 1.0 / SEMITONES_PER_OCTAVE);
    private static final double C0_FREQUENCY
        = A4_FREQUENCY / Math.pow(SEMITONE_RATIO, 57.0);

    private NoteName noteName;
    private int octave;

    public Pitch(NoteName noteName, int octave) {
        if (!acceptableValueForOctave(octave))
            throw new IllegalArgumentException(String.format(
                    "octave must be within the range: %d..%d",
                    OCTAVE_MIN, OCTAVE_MAX));

        this.noteName = noteName;
        this.octave   = octave;
    }

    private static boolean acceptableValueForOctave(int octave) {
        return octave >= OCTAVE_MIN && octave <= OCTAVE_MAX;
    }

    private int semitonesAboveC0() {
        return (this.octave * SEMITONES_PER_OCTAVE) +
               (this.noteName.toInt());
    }

    public double frequency() {
        return C0_FREQUENCY *
        		Math.pow(SEMITONE_RATIO, (double)semitonesAboveC0());
    }
}
