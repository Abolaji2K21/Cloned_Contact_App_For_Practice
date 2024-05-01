package africa.semicolon.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("Contacts")
public class Approval {

    @Id
    private String approvalId;

    private List<String> contactIds;
    private String userId;
    private Status status;
    private String author;

    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeUpdated;
}
