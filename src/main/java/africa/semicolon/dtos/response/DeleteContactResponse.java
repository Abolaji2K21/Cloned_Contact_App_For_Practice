package africa.semicolon.dtos.response;

import lombok.Data;

@Data
public class DeleteContactResponse {
    private String contactId;
    private boolean deleted;
    private String username;
}
