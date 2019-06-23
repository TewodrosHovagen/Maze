package utils.exceptions;

public class NumberParseException extends NumberFormatException{
    private String message;
    public NumberParseException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage(){
        return message;
    }
}
