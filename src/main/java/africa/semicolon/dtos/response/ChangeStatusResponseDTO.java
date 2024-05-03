package africa.semicolon.dtos.response;

import africa.semicolon.data.models.Status;
import lombok.Data;

import java.util.List;

@Data
public class ChangeStatusResponseDTO {
    private Status newStatus;
    private List<String> updatedContactIds;
}
