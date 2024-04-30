package africa.semicolon.dtos.response;

import lombok.Data;

@Data
public class CreateContactResponse {
    private String contactId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String DateCreated;
    private String Category;
}
