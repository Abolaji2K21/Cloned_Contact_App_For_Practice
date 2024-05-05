package africa.semicolon.dtos.requests;

import africa.semicolon.data.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ChangeStatusRequestDTO {
    private String approvalId;
    private String userId;
    private Status status;
}
