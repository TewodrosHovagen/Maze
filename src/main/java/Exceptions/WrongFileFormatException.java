package Exceptions;

public class WrongFileFormatException extends Exception {
    public WrongFileFormatException() {
    }

    public WrongFileFormatException(String message) {
        super(message);
    }
}
