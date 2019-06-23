package utils.exceptions;

public class WrongFileFormatException extends RuntimeException {
    private String message;

    public WrongFileFormatException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage(){
        return message;
    }
}
