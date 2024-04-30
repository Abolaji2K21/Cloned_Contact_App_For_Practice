package africa.semicolon.controller;

import africa.semicolon.data.repositories.ContactRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.*;
import africa.semicolon.dtos.response.ApiResponse;
import africa.semicolon.dtos.response.CreateContactResponse;
import africa.semicolon.dtos.response.RegisterUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactControllerTest {

    @Autowired
    private ContactController controller;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserController userController;

    @Autowired UserRepository userRepository;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testCreateContact_isSuccessful_isTrue() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);

        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getUserId();


        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userId);

        ResponseEntity<?> responseEntity = controller.createContact(createContactRequest);
        assertIsSuccessful(responseEntity, true);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));

    }

    @Test
    void testEditContact_isSuccessful_isTrue() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getUserId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userId);
        ResponseEntity<?> createContactResponseEntity = controller.createContact(createContactRequest);
        String contactId = ((CreateContactResponse) ((ApiResponse) createContactResponseEntity.getBody()).getData()).getContactId();

        EditContactRequest editContactRequest = new EditContactRequest();
        editContactRequest.setContactId(contactId);
        editContactRequest.setFirstname("NewPenIs");
        editContactRequest.setLastname("NewUp");
        editContactRequest.setUserId(userId);
        ResponseEntity<?> editContactResponseEntity = controller.editContact(editContactRequest);

        assertIsSuccessful(editContactResponseEntity, true);
        assertThat(editContactResponseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void testDeleteContact_isSuccessful_isTrue() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        assertNotNull(registrationResponse);
        String userId = registrationResponse.getUserId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userId);
        ResponseEntity<?> createContactResponseEntity = controller.createContact(createContactRequest);
        String contactId = ((CreateContactResponse) ((ApiResponse) createContactResponseEntity.getBody()).getData()).getContactId();

        DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
        deleteContactRequest.setContactId(contactId);
        deleteContactRequest.setUserId(userId);
        ResponseEntity<?> deleteContactResponseEntity = controller.deleteContact(deleteContactRequest);

        assertIsSuccessful(deleteContactResponseEntity, true);
        assertThat(deleteContactResponseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void testEditContact_isSuccessful_isFalse() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        assertNotNull(registrationResponse);
        String userId = registrationResponse.getUserId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userId);
        ResponseEntity<?> createContactResponseEntity = controller.createContact(createContactRequest);
        String contactId = ((CreateContactResponse) ((ApiResponse) createContactResponseEntity.getBody()).getData()).getContactId();

        EditContactRequest editContactRequest = new EditContactRequest();
        editContactRequest.setContactId(contactId);
        editContactRequest.setFirstname("NewPenIs");
        editContactRequest.setLastname("NewUp");
        editContactRequest.setUserId("InvalidPenis");
        ResponseEntity<?> editContactResponseEntity = controller.editContact(editContactRequest);

        assertIsSuccessful(editContactResponseEntity, false);
        assertThat(editContactResponseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testDeleteContact_isSuccessful_isFalse() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getUserId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userId);
        ResponseEntity<?> createContactResponseEntity = controller.createContact(createContactRequest);
        String contactId = ((CreateContactResponse) ((ApiResponse) createContactResponseEntity.getBody()).getData()).getContactId();

        DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
        deleteContactRequest.setContactId(contactId);
        deleteContactRequest.setUserId("InvalidPenis");
        ResponseEntity<?> deleteContactResponseEntity = controller.deleteContact(deleteContactRequest);

        assertIsSuccessful(deleteContactResponseEntity, false);
        assertThat(deleteContactResponseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }


    @Test
    void testCreateContact_isSuccessful_isFalse() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);

//        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
////        String userId = registrationResponse.getUserId();


        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId("InvalidPenis");

        ResponseEntity<?> createContactResponseEntity = controller.createContact(createContactRequest);

        assertIsSuccessful(createContactResponseEntity, false);
        assertThat(createContactResponseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));

    }

    @Test
    void testGetAllContactsByUserId_isSuccessful() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        assertNotNull(registrationResponse);
        String userId = registrationResponse.getUserId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userId);
        controller.createContact(createContactRequest);

        ResponseEntity<?> responseEntity = controller.getAllContactsByUserId(userId);
        assertIsSuccessful(responseEntity, true);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void testGetAllContactsByCategory_isSuccessful() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        assertNotNull(registrationResponse);
        String userId = registrationResponse.getUserId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setUsername("penisup");
        createContactRequest.setPhoneNumber("08165269244");
        createContactRequest.setFirstname("PenIs");
        createContactRequest.setLastname("Up");
        createContactRequest.setCategory("PenIsUpCategory");
        createContactRequest.setUserId(userId);
        controller.createContact(createContactRequest);

        ResponseEntity<?> responseEntity = controller.getAllContactsByCategory(userId, "PenIsUpCategory");
        assertIsSuccessful(responseEntity, true);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }



    private void assertIsSuccessful(ResponseEntity<?> response, boolean expected) {
        if (response.hasBody() && response.getBody() instanceof ApiResponse apiResponse) {
            boolean success = apiResponse.isSuccessful();
            assertThat(success, is(expected));
        }
    }


}