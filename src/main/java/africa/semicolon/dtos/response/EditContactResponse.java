package africa.semicolon.dtos.response;

import lombok.Data;

@Data
public class EditContactResponse {
    private String contactId;
    private String dateUpdated;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String Category;

}
