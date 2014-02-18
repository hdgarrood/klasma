public class TrackException extends RuntimeException {
    public TrackException(Throwable t) { super(t); }
    public TrackException(String msg) { super(msg); }
}
