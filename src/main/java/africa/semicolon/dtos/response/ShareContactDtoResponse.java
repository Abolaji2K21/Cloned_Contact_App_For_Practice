package africa.semicolon.dtos.response;

import africa.semicolon.data.models.Status;
import lombok.Data;

import java.util.List;

@Data
public class ShareContactDtoResponse {
    private List<String> contactId;
    private String userId;
    private String  username;
    private Status status;
}
