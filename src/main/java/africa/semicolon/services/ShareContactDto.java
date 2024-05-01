package africa.semicolon.services;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ShareContactDto {
    private List<String> contactId;

    private String userId;
    private String  author;
}
