package africa.semicolon.dtos.response;

import lombok.Data;

@Data
public class RegisterUserResponse {
    private String userId;
    private String username;
    private String dateRegistered;
    private boolean loggedIn;

}
