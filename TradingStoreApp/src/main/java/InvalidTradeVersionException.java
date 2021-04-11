public class InvalidTradeVersionException extends RuntimeException {

    private final String id;

    public InvalidTradeVersionException(final String id) {
        super("Invalid Trade: " + id);
        this.id = id;
    }
}