package africa.semicolon.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogoutUserResponse {
    private String id;
    private String username;
    private boolean loggedIn;


}
