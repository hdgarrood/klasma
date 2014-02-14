public class EndOfChannelException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EndOfChannelException(Throwable t) {
        super(t);
    }
}