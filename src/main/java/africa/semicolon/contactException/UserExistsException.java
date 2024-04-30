package africa.semicolon.contactException;

public class UserExistsException extends BigContactException{
    public UserExistsException(String message) {
        super(message);
    }
}