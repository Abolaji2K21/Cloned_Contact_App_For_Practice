package africa.semicolon.dtos.requests;

import lombok.Data;

@Data
public class DeleteContactRequest {
    private String username;
    private String phoneNumber;
    private String category;
    private String contactId;
    private String userId;

}
