package africa.semicolon.dtos.response;

import lombok.Data;

@Data
public class SuggestContactResponse {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
