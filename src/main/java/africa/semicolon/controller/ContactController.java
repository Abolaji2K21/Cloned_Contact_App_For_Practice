package africa.semicolon.controller;

import africa.semicolon.contactException.BigContactException;
import africa.semicolon.data.models.Contact;
import africa.semicolon.data.repositories.ContactRepository;
import africa.semicolon.dtos.requests.CreateContactRequest;
import africa.semicolon.dtos.requests.DeleteContactRequest;
import africa.semicolon.dtos.requests.EditContactRequest;
import africa.semicolon.dtos.response.ApiResponse;
import africa.semicolon.dtos.response.CreateContactResponse;
import africa.semicolon.dtos.response.DeleteContactResponse;
import africa.semicolon.dtos.response.EditContactResponse;
import africa.semicolon.services.ContactService;
import africa.semicolon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/Contact")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createContact(@RequestBody CreateContactRequest createContactRequest) {
        try {
            CreateContactResponse result = contactService.createContactForUser(createContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (BigContactException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }


    @PostMapping("/edit")
    public ResponseEntity<?> editContact(@RequestBody EditContactRequest editContactRequest) {
        try {
            EditContactResponse result = contactService.editContactForUser(editContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (BigContactException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteContact(@RequestBody DeleteContactRequest deleteContactRequest) {
        try {
            DeleteContactResponse result = contactService.deleteContactForUser(deleteContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (BigContactException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/getAllByUserId/{userId}")
    public ResponseEntity<?> getAllContactsByUserId(@PathVariable(name = "userId") String userId) {
        try {
            Optional<Contact> result = contactService.getAllContactsByUserId(userId);
                   return new ResponseEntity<>(new ApiResponse(true, result.get()), OK);
//                    new ResponseEntity<>(new ApiResponse(false, "No contacts found"), NOT_FOUND);
        } catch (BigContactException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllByCategory/{userId}/{category}")
    public ResponseEntity<?> getAllContactsByCategory(@PathVariable(name = "userId")String userId, @PathVariable(name = "category") String category) {
        try {
            List<Contact> result = contactService.getAllContactsByCategory(userId, category);
//            return !result.isEmpty() ?
                    return new ResponseEntity<>(new ApiResponse(true, result),OK);
//                    new ResponseEntity<>(new ApiResponse(false, "No contacts found for the given category"), NOT_FOUND);
        } catch (BigContactException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()),BAD_REQUEST);
        }
    }


}
