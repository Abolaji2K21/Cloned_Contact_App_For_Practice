package africa.semicolon.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ShareContactDto {
    private List<String> contactId;
    private String userId;
    private String  username;
}
