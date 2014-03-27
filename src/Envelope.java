public interface Envelope {
    // This method should:
    //   * Take a double between 0 and 1 (how far through the note we are)
    //   * return a double between 0 and 1 representing the amplitude
    //     adjustment to be made at that time.
    public double at(double t);
}
