package africa.semicolon.services;

import africa.semicolon.contactException.BigContactException;
import africa.semicolon.data.models.Approval;
import africa.semicolon.data.models.Contact;
import africa.semicolon.data.models.Status;
import africa.semicolon.data.models.User;
import africa.semicolon.data.repositories.ApprovalRepository;
import africa.semicolon.data.repositories.ContactRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.ShareContactDto;
import africa.semicolon.services.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApprovalServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ApprovalService approvalService;



    @BeforeEach
    void setUp() {
        approvalRepository.deleteAll();
        userRepository.deleteAll();
        contactRepository.deleteAll();

    }

    @Test
    void testShare_UserDoesNotExist() {
        ShareContactDto shareContactDto = new ShareContactDto();
        shareContactDto.setUserId("invalidUserId");
        shareContactDto.setUsername("username");
        shareContactDto.setContactId(Collections.singletonList("contactId"));

        assertThrows(RuntimeException.class, () -> approvalService.share(shareContactDto));
    }

    @Test
    void testShare_UserExists() {
        String userId = "validUserId";
        User user = new User();
        user.setUserId(userId);
        userRepository.save(user);

        ShareContactDto shareContactDto = new ShareContactDto();
        shareContactDto.setUserId(userId);
        shareContactDto.setUsername("username");
        shareContactDto.setContactId(Collections.singletonList("contactId"));

        approvalService.share(shareContactDto);

        List<Approval> approvals = approvalRepository.findByUserId(userId);
        Approval approval;
        if (!approvals.isEmpty()) {
            approval = approvals.get(0);
        } else {
            throw new BigContactException("Approval not found for This user ID: ");
        }

        assertEquals("username", approval.getUsername());
        assertTrue(approval.getContactIds().contains("contactId"));
        assertEquals(Status.PENDING, approval.getStatus());

    }

    @Test
    public void testGetApprovals_TwoApprovals() {
        Approval approval1 = new Approval();
        approval1.setUserId("userOne");
        approval1.setUsername("PenIsUp");
        approval1.setStatus(Status.PENDING);
        approvalRepository.save(approval1);

        Approval approval2 = new Approval();
        approval2.setUserId("userOne");
        approval2.setUsername("PenIsUp");
        approval2.setStatus(Status.APPROVED);
        approvalRepository.save(approval2);

        List<Approval> approvals = approvalService.getApprovals("userOne");
        assertEquals(2, approvals.size());
    }

    @Test
    void testGetApprovals_NoApprovals() {
        String userId = "invalidUserId";
        List<Approval> actualApprovals = approvalService.getApprovals(userId);
        assertTrue(actualApprovals.isEmpty());
    }

    @Test
    public void testChangeStatus() {
        Approval approval = new Approval();
        approval.setUserId("user1");
        approval.setUsername("PenIsUp");
        approval.setStatus(Status.PENDING);

        List<Contact> contacts = new ArrayList<>();
        Contact contact1 = new Contact();
        contact1.setUserId("user2");
        contact1.setFirstName("PenIsUp");
        contact1.setLastName("AndActive");
        contacts.add(contact1);
        contactRepository.save(contact1);

        contactRepository.saveAll(contacts);

        List<String> contactIds = new ArrayList<>();
        contactIds.add(contact1.getContactId());
        approval.setContactIds(contactIds);
        approvalRepository.save(approval);

        approvalService.changeStatus(Status.APPROVED, approval.getApprovalId(), "user1");

        Approval updatedApproval = approvalRepository.findByApprovalIdAndUserId(approval.getApprovalId(), "user1").orElse(null);
        assertEquals(Status.APPROVED, updatedApproval.getStatus());

    }

}
