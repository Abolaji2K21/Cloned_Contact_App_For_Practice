package africa.semicolon.contactException;

public class InvalidPassCodeException extends BigContactException{
    public InvalidPassCodeException(String message) {
        super(message);
    }
}