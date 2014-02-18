public enum NoteName {
    C      (0),
    CSharp (1),
    DFlat  (1),
    D      (2),
    DSharp (3),
    EFlat  (3),
    E      (4),
    F      (5),
    FSharp (6),
    GFlat  (6),
    G      (7),
    GSharp (8),
    AFlat  (8),
    A      (9),
    ASharp (10),
    BFlat  (10),
    B      (11);

    private final int value;

    NoteName(int value) {
        this.value = value;
    }

    public int toInt() {
        return this.value;
    }

    public static NoteName parse(String str) {
        switch (str) {
        case "C":
            return C;
        case "C#":
            return CSharp;
        case "Db":
            return DFlat;
        case "D":
            return D;
        case "D#":
            return DSharp;
        case "Eb":
            return EFlat;
        case "E":
            return E;
        case "F":
            return F;
        case "F#":
            return FSharp;
        case "Gb":
            return GFlat;
        case "G":
            return G;
        case "G#":
            return GSharp;
        case "Ab":
            return AFlat;
        case "A":
            return A;
        case "A#":
            return ASharp;
        case "Bb":
            return BFlat;
        case "B":
            return B;
        }
        return null;
    }
}
