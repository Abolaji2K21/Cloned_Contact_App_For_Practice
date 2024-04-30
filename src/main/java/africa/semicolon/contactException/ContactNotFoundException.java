package africa.semicolon.contactException;

public class ContactNotFoundException extends BigContactException{
    public ContactNotFoundException(String message) {
        super(message);
    }
}