package Utils;

public class WrongFileFormatException extends RuntimeException {
    public WrongFileFormatException() {
    }

    public WrongFileFormatException(String message) {
        super(message);
    }
}
