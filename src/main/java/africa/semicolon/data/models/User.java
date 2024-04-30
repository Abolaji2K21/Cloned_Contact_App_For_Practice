package africa.semicolon.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("Users")
@RequiredArgsConstructor
public class User {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateUpdated = LocalDateTime.now();
    private boolean loggedIn;
    private List<Contact> contacts;

}
