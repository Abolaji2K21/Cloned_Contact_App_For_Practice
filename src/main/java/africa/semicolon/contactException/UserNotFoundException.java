package africa.semicolon.contactException;

public class UserNotFoundException extends BigContactException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
