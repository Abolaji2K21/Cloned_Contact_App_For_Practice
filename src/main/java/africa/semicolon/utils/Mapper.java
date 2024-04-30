package africa.semicolon.utils;

import africa.semicolon.contactException.BigContactException;
//import africa.semicolon.data.models.Category;
import africa.semicolon.data.models.Contact;
import africa.semicolon.data.models.User;
import africa.semicolon.dtos.requests.CreateContactRequest;
import africa.semicolon.dtos.requests.EditContactRequest;
import africa.semicolon.dtos.requests.RegisterUserRequest;
import africa.semicolon.dtos.response.*;
import africa.semicolon.services.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {
    public static User map(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setFirstName(registerUserRequest.getFirstname());
        user.setLastName(registerUserRequest.getLastname());
        user.setPassword(registerUserRequest.getPassword());
        user.setUsername(registerUserRequest.getUsername());
        return user;
    }

    public static RegisterUserResponse map(User user) {
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUsername(user.getUsername());
        registerUserResponse.setUserId(user.getUserId());
        registerUserResponse.setDateRegistered(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(user.getDateCreated()));
        return registerUserResponse;
    }
    public static CreateContactResponse mapCreateContactResponse(Contact savedContact) {
        CreateContactResponse response = new CreateContactResponse();
        response.setContactId(savedContact.getContactId());
        response.setFirstName(savedContact.getFirstName());
        response.setLastName(savedContact.getLastName());
        response.setPhoneNumber(savedContact.getPhoneNumber());
        response.setCategory(savedContact.getCategory());
        response.setDateCreated(String.valueOf(savedContact.getDateTimeCreated()));
        return response;
    }

    public static Contact mapContact(CreateContactRequest createContactRequest, String username, User user) {
        Contact contact = new Contact();
        contact.setUsername(username);
        contact.setPhoneNumber(createContactRequest.getPhoneNumber());
        contact.setFirstName(createContactRequest.getFirstname());
        contact.setLastName(createContactRequest.getLastname());
        contact.setCategory(createContactRequest.getCategory());
        contact.setUserId(user.getUserId());
        return contact;
    }

    public static EditContactResponse mapEditContactResponse(Contact updatedContact) {
        EditContactResponse response = new EditContactResponse();
        response.setContactId(updatedContact.getContactId());
        response.setFirstName(updatedContact.getFirstName());
        response.setLastName(updatedContact.getLastName());
        response.setPhoneNumber(updatedContact.getPhoneNumber());
        response.setCategory(updatedContact.getCategory());
        response.setDateUpdated(updatedContact.getDateTimeUpdated().toString());
        return response;
    }
    public static void mapEdit(EditContactRequest editContactRequest, Contact existingContact) {
        existingContact.setPhoneNumber(editContactRequest.getPhoneNumber());
        existingContact.setFirstName(editContactRequest.getFirstname());
        existingContact.setLastName(editContactRequest.getLastname());
        existingContact.setCategory(editContactRequest.getCategory());
        existingContact.setDateTimeUpdated(LocalDateTime.now());
    }
    public static DeleteContactResponse mapDeleteContactResponse(Contact existingContact, String username) {
        DeleteContactResponse response = new DeleteContactResponse();
        response.setContactId(existingContact.getContactId());
        response.setDeleted(true);
        response.setUsername(username);

        return response;
    }

    public static UpdateUserResponse mapUpdateUserResponse(User user) {
        UpdateUserResponse response = new UpdateUserResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setFirstname(user.getFirstName());
        response.setLastname(user.getLastName());
        response.setDateUpdated(user.getDateUpdated().toString());
        response.setLoggedIn(user.isLoggedIn());
        return response;
    }
//    public static CreateCategoryResponse mapCreateCategoryResponse(Category category) {
//        CreateCategoryResponse response = new CreateCategoryResponse();
//        response.setCategoryId(category.getCategoryId());
//        response.setDescription(category.getDescription());
//        response.setUsername(category.getUsername());
//        return response;
//    }
//    public static EditCategoryResponse mapEditCategoryResponse(Category updatedCategory) {
//        EditCategoryResponse response = new EditCategoryResponse();
//        response.setCategoryId(updatedCategory.getCategoryId());
//        response.setDescription(updatedCategory.getDescription());
//        response.setUsername(updatedCategory.getUsername());
//        return response;
//    }

}