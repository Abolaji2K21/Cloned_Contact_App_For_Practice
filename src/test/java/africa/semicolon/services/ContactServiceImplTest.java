package africa.semicolon.services;

import africa.semicolon.contactException.BigContactException;
import africa.semicolon.contactException.UserNotFoundException;
import africa.semicolon.data.models.Contact;
import africa.semicolon.data.models.User;
import africa.semicolon.data.repositories.ContactRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.*;
import africa.semicolon.dtos.response.CreateContactResponse;
import africa.semicolon.dtos.response.DeleteContactResponse;
import africa.semicolon.dtos.response.EditContactResponse;
import africa.semicolon.dtos.response.RegisterUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContactServiceImplTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        contactRepository.deleteAll();
    }

    @Test
    public void testCreateContact_When_UserIsNotRegistered() {
        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");

        assertThrows(BigContactException.class, () -> contactService.createContactForUser(createContactRequest));
    }

    @Test
    public void testCreateContact_When_UserIsRegisteredButNotLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("PenIs");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");

        assertThrows(BigContactException.class, () -> contactService.createContactForUser(createContactRequest));
    }

    @Test
    public void testCreateContact_When_UserIsRegisteredAndLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userResponse.getUserId());

        CreateContactResponse response = contactService.createContactForUser(createContactRequest);
        assertEquals("08165269244", response.getPhoneNumber());
        assertEquals("PenIs", response.getFirstName());
        assertEquals("Up", response.getLastName());
        assertEquals("PenIsUpCategory", response.getCategory());
    }

    @Test
    public void testEditContact_When_UserIsNotLoggedIn() {
        EditContactRequest editContactRequest = new EditContactRequest();
        editContactRequest.setContactId(editContactRequest.getContactId());
        editContactRequest.setUsername("penisup");
        editContactRequest.setPhoneNumber("08165269244");
        editContactRequest.setFirstname("PenIs");
        editContactRequest.setLastname("Up");
        editContactRequest.setCategory("PenIsUpCategory");


        assertThrows(BigContactException.class, () -> contactService.editContactForUser(editContactRequest));
    }

    @Test
    public void testEditContact_When_UserIsLoggedInButNotAuthorized() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse  userResponse = userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userResponse.getUserId());
        CreateContactResponse response = contactService.createContactForUser(createContactRequest);

        EditContactRequest editContactRequest = new EditContactRequest();
        editContactRequest.setUsername("penisdown");
        editContactRequest.setContactId(response.getContactId());
        editContactRequest.setPhoneNumber("08165269244");
        editContactRequest.setFirstname("PenIs");
        editContactRequest.setLastname("Down");
        editContactRequest.setCategory("PenIsUpCategory");
        editContactRequest.setUserId("fake user");

        assertThrows(BigContactException.class, () -> contactService.editContactForUser(editContactRequest));
    }

    @Test
    public void testEditContact_When_UserIsLoggedInAndAuthorized() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userResponse.getUserId());
        CreateContactResponse response = contactService.createContactForUser(createContactRequest);

        EditContactRequest editContactRequest = new EditContactRequest();
        editContactRequest.setUsername("penisup");
        editContactRequest.setContactId(response.getContactId());
        editContactRequest.setPhoneNumber("08165269244");
        editContactRequest.setFirstname("PenIs");
        editContactRequest.setLastname("Down");
        editContactRequest.setCategory("PenIsUpCategory");
        editContactRequest.setUserId(userResponse.getUserId());

        EditContactResponse editContactResponse = contactService.editContactForUser(editContactRequest);
        assertEquals(response.getContactId(), editContactResponse.getContactId());
        assertEquals("08165269244", editContactResponse.getPhoneNumber());
        assertEquals("PenIs", editContactResponse.getFirstName());
        assertEquals("Down", editContactResponse.getLastName());
        assertEquals("PenIsUpCategory", editContactResponse.getCategory());
    }

    @Test
    public void testingDeleteContactWhenUserIsNotRegistered() {
        DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
        deleteContactRequest.setUsername("penisup");
        deleteContactRequest.setContactId("theyplaymyfans");

        assertThrows(BigContactException.class, () -> contactService.deleteContactForUser(deleteContactRequest));
    }

    @Test
    public void testingDeleteContactWhenUserIsRegisteredButNotLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
        deleteContactRequest.setUsername("penisup");
        deleteContactRequest.setContactId("Theyplaymyfans");

        assertThrows(BigContactException.class, () -> contactService.deleteContactForUser(deleteContactRequest));
    }

    @Test
    public void testingDeleteContactWhenUserIsRegisteredAndLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userResponse.getUserId());
        CreateContactResponse response = contactService.createContactForUser(createContactRequest);

        DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
        deleteContactRequest.setUsername("penisup");
        deleteContactRequest.setContactId(response.getContactId());
        deleteContactRequest.setUserId(userResponse.getUserId());
        DeleteContactResponse deleteContactResponse = contactService.deleteContactForUser(deleteContactRequest);
        assertEquals(response.getContactId(), deleteContactResponse.getContactId());
        assertTrue(deleteContactResponse.isDeleted());
    }

    @Test
    void testThatAnotherUserCannotDeleteContact() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userResponse.getUserId());
        CreateContactResponse response = contactService.createContactForUser(createContactRequest);

        RegisterUserRequest registerRequest2 = new RegisterUserRequest();
        registerRequest2.setFirstname("pen");
        registerRequest2.setLastname("isdown");
        registerRequest2.setUsername("penisdown");
        registerRequest2.setPassword("WrongHole");
        userService.register(registerRequest2);

        LoginUserRequest loginRequest2 = new LoginUserRequest();
        loginRequest2.setUsername("penisdown");
        loginRequest2.setPassword("WrongHole");
        userService.login(loginRequest2);

        DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
        deleteContactRequest.setUsername("penisdown");
        deleteContactRequest.setContactId(response.getContactId());
        deleteContactRequest.setUserId("wrongPen");

        assertThrows(BigContactException.class, () -> contactService.deleteContactForUser(deleteContactRequest));
    }
    @Test
    void testGetAllContactsByUserId_WhenUserExistsAndHasContacts() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userResponse.getUserId());
        contactService.createContactForUser(createContactRequest);

        Optional<Contact> contacts = contactService.getAllContactsByUserId(userResponse.getUserId());
        assertTrue(contacts.isPresent());
//        assertEquals(1, contacts.get().);
    }

    @Test
    void testGetAllContactsByUserId_WhenUserExistsButHasNoContacts() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        Optional<Contact> contacts = contactService.getAllContactsByUserId(userResponse.getUserId());
        assertTrue(contacts.isEmpty());
    }

    @Test
    void testGetAllContactsByUserId_WhenUserDoesNotExist() {
        assertThrows(UserNotFoundException.class, () -> contactService.getAllContactsByUserId("invalidUserId"));
    }

    @Test
    void testGetAllContactsByCategory_WhenUserExistsAndHasContacts() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userResponse.getUserId());
        contactService.createContactForUser(createContactRequest);

        List<Contact> contacts = contactService.getAllContactsByCategory(userResponse.getUserId(), "PenIsUpCategory");
        assertEquals(1, contacts.size());
    }

    @Test
    void testGetAllContactsByCategory_WhenUserExistsButHasNoContacts() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        List<Contact> contacts = contactService.getAllContactsByCategory(userResponse.getUserId(), "PenIsUpCategory");
        assertTrue(contacts.isEmpty());
    }

    @Test
    void testGetAllContactsByCategory_WhenUserDoesNotExist() {
        assertThrows(UserNotFoundException.class, () -> contactService.getAllContactsByCategory("invalidUserId", "PenIsUpCategory"));
    }


}
