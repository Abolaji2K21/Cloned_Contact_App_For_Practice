package africa.semicolon.dtos.requests;

import lombok.Data;

@Data
public class EditContactRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String category;
    private String contactId;
    private String userId;

}
