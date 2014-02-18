public class TrackException extends RuntimeException {
    private static final long serialVersionUID = 1l;
    public TrackException(Throwable t) { super(t); }
    public TrackException(String msg) { super(msg); }
}
