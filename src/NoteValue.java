public class NoteValue {
    public static NoteValue Breve          = new NoteValue(2.0);
    public static NoteValue Semibreve      = new NoteValue(1.0);
    public static NoteValue Minim          = new NoteValue(1.0/2);
    public static NoteValue Crotchet       = new NoteValue(1.0/4);
    public static NoteValue Quaver         = new NoteValue(1.0/8);
    public static NoteValue Semiquaver     = new NoteValue(1.0/16);
    public static NoteValue Demisemiquaver = new NoteValue(1.0/32);

    // The ratio of this NoteValue's length to a semibreve's.
    private double value;

    public NoteValue(double value) {
        this.value = value;
    }

    public double length() {
        return this.value;
    }

    public NoteValue dotted() {
        return new NoteValue(this.value * 1.5);
    }
}
