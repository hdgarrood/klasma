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
}
