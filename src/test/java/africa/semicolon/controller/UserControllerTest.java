package africa.semicolon.controller;

import africa.semicolon.contactException.BigContactException;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.LoginUserRequest;
import africa.semicolon.dtos.requests.LogoutUserRequest;
import africa.semicolon.dtos.requests.RegisterUserRequest;
import africa.semicolon.dtos.requests.UpdateUserRequest;
import africa.semicolon.dtos.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRegister_isSuccessful_isTrue() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("PenIsUp");
        registerRequest.setPassword("Holes");

        ResponseEntity<?> response = userController.registerUser(registerRequest);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void testLogin_isSuccessful_isTrue() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("PenIsUp");
        registerRequest.setPassword("Holes");
        userController.registerUser(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("PenIsUp");
        loginRequest.setPassword("Holes");

        ResponseEntity<?> response = userController.login(loginRequest);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void testLogout_isSuccessful_isTrue() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("PenIsUp");
        registerRequest.setPassword("Holes");
        userController.registerUser(registerRequest);

        LogoutUserRequest logoutRequest = new LogoutUserRequest();
        logoutRequest.setUsername("PenIsUp");

        ResponseEntity<?> response = userController.logout(logoutRequest);
        assertIsSuccessful(response, true);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

//    @Test
//    void testUpdateUserProfile_isSuccessful_isTrue() {
//        RegisterUserRequest registerRequest = new RegisterUserRequest();
//        registerRequest.setFirstname("PenIs");
//        registerRequest.setLastname("Up");
//        registerRequest.setUsername("PenIsUp");
//        registerRequest.setPassword("Holes");
//        ResponseEntity<?> registerResponse = userController.registerUser(registerRequest);
//
//        UpdateUserRequest updateRequest = new UpdateUserRequest();
//        updateRequest.setUserId(registerResponse.getBody().);
//        updateRequest.setFirstname("NewFirstname");
//        updateRequest.setLastname("NewLastname");
//        updateRequest.setUsername("NewUsername");
//
//        ResponseEntity<?> response = userController.updateUserProfile(updateRequest);
//        assertIsSuccessful(response, true);
//        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
//    }
@Test
void testRegister_isSuccessful_isFalse() {
    RegisterUserRequest registerRequest = new RegisterUserRequest();
    registerRequest.setFirstname("PenIs");
    registerRequest.setLastname("Up");
    registerRequest.setUsername("PenIsUp");
    registerRequest.setPassword("Holes");

    userController.registerUser(registerRequest);

    ResponseEntity<?> response = userController.registerUser(registerRequest);
    assertIsSuccessful(response, false);
    assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
}

    @Test
    void testLogin_isSuccessful_isFalse() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("PenIsUp");
        registerRequest.setPassword("Holes");
        userController.registerUser(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("PenIsUp");
        loginRequest.setPassword("WrongPassword");

        ResponseEntity<?> response = userController.login(loginRequest);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testLogout_isSuccessful_isFalse() {
        LogoutUserRequest logoutRequest = new LogoutUserRequest();
        logoutRequest.setUsername("NonExistingUser");

        ResponseEntity<?> response = userController.logout(logoutRequest);
        assertIsSuccessful(response, false);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }


    private void assertIsSuccessful(ResponseEntity<?> response, boolean expected) {
        if (response.hasBody() && response.getBody() instanceof ApiResponse apiResponse) {
            boolean success = apiResponse.isSuccessful();
            assertThat(success, is(expected));
        }
    }
}
