package africa.semicolon.services;

import africa.semicolon.data.models.Approval;
import africa.semicolon.data.models.Contact;
import africa.semicolon.data.models.Status;
import africa.semicolon.data.repositories.ApprovalRepository;
import africa.semicolon.data.repositories.ContactRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.ShareContactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl {

    private final ApprovalRepository approvalRepository;
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    public void share(ShareContactDto shareContactDto) {
        if (!userRepository.existsById(shareContactDto.getUserId())) {
            throw new RuntimeException("user does not exist");
        }
        Approval approval = new Approval();
        approval.setStatus(Status.PENDING);
        approval.setAuthor(shareContactDto.getUsername());
        approval.setContactIds(shareContactDto.getContactId());
        approval.setUserId(shareContactDto.getUserId());
        approvalRepository.save(approval);
    }

    public List<Approval> getApprovals(String userId) {
        return approvalRepository.findByUserId(userId);
    }

    public void changeStatus(Status status, String approvalId, String userId) {
        Approval approval = approvalRepository.findByApprovalIdAndUserId(approvalId, userId).orElseThrow(() -> new RuntimeException());
        if (status.equals(Status.APPROVED)) {
            for (String id : approval.getContactIds()) {
                Contact contact = contactRepository.findById(id).get();
                Contact newContact = new Contact();
                newContact.setUsername(contact.getUsername());
                newContact.setFirstName(contact.getFirstName());
                newContact.setLastName(contact.getLastName());
                newContact.setUserId(approval.getUserId());
                contactRepository.save(newContact);
                approval.setStatus(status);
            }
        } else {
            approval.setStatus(status);
        }
        approvalRepository.save(approval);
    }
}
