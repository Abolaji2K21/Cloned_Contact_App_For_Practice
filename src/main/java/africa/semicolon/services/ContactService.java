package africa.semicolon.services;

import africa.semicolon.data.models.Contact;
import africa.semicolon.data.models.User;
import africa.semicolon.dtos.requests.CreateContactRequest;
import africa.semicolon.dtos.requests.DeleteContactRequest;
import africa.semicolon.dtos.requests.EditContactRequest;
import africa.semicolon.dtos.response.CreateContactResponse;
import africa.semicolon.dtos.response.DeleteContactResponse;
import africa.semicolon.dtos.response.EditContactResponse;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    CreateContactResponse createContactForUser(CreateContactRequest createContactRequest);

    EditContactResponse editContactForUser(EditContactRequest editContactRequest);

    User findUserBy(String username);

    DeleteContactResponse deleteContactForUser(DeleteContactRequest deleteContactRequest);
    Optional<Contact> getAllContactsByUserId(String userId);

    List<Contact> getAllContactsByCategory(String userId, String category);


}
