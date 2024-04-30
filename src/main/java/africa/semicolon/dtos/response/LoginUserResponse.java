package africa.semicolon.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponse {
    private final String id;
    private final String username;
    private boolean loggedIn;

}