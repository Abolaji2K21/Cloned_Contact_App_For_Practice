package africa.semicolon.services;

import africa.semicolon.contactException.BigContactException;
import africa.semicolon.contactException.ContactNotFoundException;
import africa.semicolon.contactException.UserNotFoundException;
import africa.semicolon.data.models.Contact;
import africa.semicolon.data.models.User;
import africa.semicolon.data.repositories.ContactRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.CreateContactRequest;
import africa.semicolon.dtos.requests.DeleteContactRequest;
import africa.semicolon.dtos.requests.EditContactRequest;
import africa.semicolon.dtos.response.CreateContactResponse;
import africa.semicolon.dtos.response.DeleteContactResponse;
import africa.semicolon.dtos.response.EditContactResponse;
import africa.semicolon.dtos.response.SuggestContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static africa.semicolon.utils.Mapper.*;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public CreateContactResponse createContactForUser(CreateContactRequest createContactRequest) {
        String userId = createContactRequest.getUserId();
        User user = findUserBy(userId);
        checkUserStatus(user.getUsername());
        validateCreateContactRequest(createContactRequest);
        Contact contact = mapContact(createContactRequest, user.getUsername(), user);
        contact.setDateTimeCreated(LocalDateTime.now());
        Contact savedContact = contactRepository.save(contact);
        return mapCreateContactResponse(savedContact);
    }

    @Override
    public EditContactResponse editContactForUser(EditContactRequest editContactRequest) {
        String userId = editContactRequest.getUserId();
        User user = findUserBy(userId);
        checkUserStatus(user.getUsername());
        Contact existingContact = checkEditContactStatus(editContactRequest, user);
        mapEdit(editContactRequest, existingContact);
        Contact updatedContact = contactRepository.save(existingContact);
        return mapEditContactResponse(updatedContact);
    }

    @Override
    public User findUserBy(String userId) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public DeleteContactResponse deleteContactForUser(DeleteContactRequest deleteContactRequest) {
        String userId = deleteContactRequest.getUserId();
        User user = findUserBy(userId);
        checkUserStatus(user.getUsername());
        Contact existingContact = checkDeleteContactStatus(deleteContactRequest, user);
        contactRepository.delete(existingContact);
        return mapDeleteContactResponse(existingContact, user.getUsername());
    }

    @Override
    public List<Contact> getAllContactsByUserId(String userId) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found ");
        }
        return contactRepository.findByUserId(userId);
    }

    @Override
    public List<Contact> getAllContactsByCategory(String userId,String category) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found ");
        }
        return contactRepository.findAllByUserIdAndCategory(userId,category);
    }

    private void validateCreateContactRequest(CreateContactRequest createContactRequest) {
        String userId = createContactRequest.getUserId();
        User user = findUserBy(userId);
        if(user.getUsername()== null || user.getUsername().isEmpty()){
            throw new BigContactException("Username cannot be null or empty");
        }
        String phoneNumber = createContactRequest.getPhoneNumber();
        if (!phoneNumber.matches("^(0|234|\\+234)?[7-9][0-1]\\d{8}$")){
            throw new BigContactException("Invalid phone number format");
        }
        boolean contactExistsByPhoneNumber = contactRepository.existsByPhoneNumber(phoneNumber);
        if (contactExistsByPhoneNumber) {
            throw new BigContactException("Contact already exists");
        }
    }

    private void checkUserStatus(String username) {
        if (!userService.isUserRegistered(username)) {
            throw new BigContactException("User not registered");
        }

        if (!userService.isUserLoggedIn(username)) {
            throw new BigContactException("User not logged in");
        }
    }

    private Contact checkEditContactStatus(EditContactRequest editContactRequest, User user) {
        String contactId = editContactRequest.getContactId();
        Contact existingContact = contactRepository.findContactByContactIdAndUserId(contactId, user.getUserId());
        if (existingContact == null) {
            throw new BigContactException("Contact with ID " + contactId + " not found");
        }

        if (!existingContact.getUserId().equals(user.getUserId())) {
            throw new BigContactException("You are not authorized to edit this contact");
        }
        return existingContact;
    }

    private Contact checkDeleteContactStatus(DeleteContactRequest deleteContactRequest, User user) {
        String contactId = deleteContactRequest.getContactId();
        Contact existingContact = contactRepository.findContactByContactIdAndUserId(contactId, user.getUserId());

        if (existingContact == null) {
            throw new BigContactException("Contact with ID " + contactId + " not found");
        }

        if (!existingContact.getUserId().equals(user.getUserId())) {
            throw new BigContactException("You are not authorized to delete this contact");
        }
        return existingContact;
    }
    @Override
    public SuggestContactResponse suggestContactsByPhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber);

        Contact matchingContact = contactRepository.findFirstByPhoneNumber(phoneNumber);
        if (matchingContact == null) {
            throw new ContactNotFoundException("Contact not found for phone number: " + phoneNumber);
        }

        SuggestContactResponse response = new SuggestContactResponse();
        response.setFirstName(matchingContact.getFirstName());
        response.setLastName(matchingContact.getLastName());
        response.setPhoneNumber(matchingContact.getPhoneNumber());

        return response;
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new BigContactException("Phone number cannot be empty or null");
        }
        if (!phoneNumber.matches("^(0|234|\\+234)?[7-9][0-1]\\d{8}$")){
            throw new BigContactException("Invalid phone number format");
        }
    }


    @Override
    public List<Contact> findContactsByPartialFirstName(String userId, String partialFirstName) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        List<Contact> matchedContacts = contactRepository.findByFirstNameRegex(".*" + partialFirstName + ".*");
        if (matchedContacts.isEmpty()) {
            throw new BigContactException("No contacts found with partial first name: " + partialFirstName);
        }
        return matchedContacts;
    }


    @Override
    public List<Contact> findContactsByPartialLastName(String userId,String partialLastName) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found ");
        }

        List<Contact> matchedContacts = contactRepository.findByLastNameRegex(".*" + partialLastName + ".*");
        if (matchedContacts.isEmpty()) {
            throw new BigContactException("No contacts found with partial last name: " + partialLastName);
        }
        return matchedContacts;

    }
    @Override
    public List<Contact> findContactsByPartialPhoneNumber(String userId,String partialPhoneNumber) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found ");
        }
        List<Contact> matchedContacts = contactRepository.findByPhoneNumberRegex(".*" + partialPhoneNumber + ".*");
        if (matchedContacts.isEmpty()) {
            throw new BigContactException("No contacts found.");
        }
        return matchedContacts;
    }



}
