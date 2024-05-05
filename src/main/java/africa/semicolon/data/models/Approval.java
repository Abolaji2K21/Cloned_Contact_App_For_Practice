package africa.semicolon.data.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Approval {

    @Id
    private String approvalId;

    private List<String> contactIds;
    private String userId;
    private Status status;
    private String username;

    private LocalDateTime dateTimeCreated = LocalDateTime.now();
//    private LocalDateTime dateTimeUpdated;

}
