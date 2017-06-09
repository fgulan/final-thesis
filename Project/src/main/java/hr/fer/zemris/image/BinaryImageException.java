package hr.fer.zemris.image;

/**
 * @author Filip Gulan
 */
public class BinaryImageException extends RuntimeException {

    public BinaryImageException() {
        super();
    }

    public BinaryImageException(String message) {
        super(message);
    }

    public BinaryImageException(Throwable cause) {
        super(cause);
    }

    public BinaryImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
