package africa.semicolon.services;

import africa.semicolon.contactException.BigContactException;
import africa.semicolon.data.models.Approval;
import africa.semicolon.data.models.Contact;
import africa.semicolon.data.models.Status;
import africa.semicolon.data.repositories.ApprovalRepository;
import africa.semicolon.data.repositories.ContactRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.ShareContactDto;
import africa.semicolon.dtos.response.ChangeStatusResponseDTO;
import africa.semicolon.dtos.response.ShareContactDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public ShareContactDtoResponse share(ShareContactDto shareContactDto) {
        if (!userRepository.existsById(shareContactDto.getUserId())) {
            throw new BigContactException("user does not exist");
        }
        Approval approval = new Approval();
        approval.setStatus(Status.PENDING);
        approval.setUsername(shareContactDto.getUsername());
        approval.setContactIds(shareContactDto.getContactId());
        approval.setUserId(shareContactDto.getUserId());
        approvalRepository.save(approval);

        ShareContactDtoResponse shareContactDtoResponse = new ShareContactDtoResponse();
        shareContactDtoResponse.setUserId(shareContactDto.getUserId());
        shareContactDtoResponse.setUsername(shareContactDto.getUsername());
        shareContactDtoResponse.setContactId(shareContactDto.getContactId());
        shareContactDtoResponse.setStatus(Status.PENDING);

        return shareContactDtoResponse;
    }


    @Override
    public List<Approval> getApprovals(String userId) {
        return approvalRepository.findByUserId(userId);
    }


    @Override
    public ChangeStatusResponseDTO changeStatus(Status status, String approvalId, String userId) {
        Approval approval = approvalRepository.findByApprovalIdAndUserId(approvalId, userId)
                .orElseThrow(() -> new BigContactException("Approval not found for this user"));

        if (status.equals(Status.APPROVED)) {
            List<String> contactIds = approval.getContactIds();
            for (int count = 0; count < contactIds.size(); count++) {
                String id = contactIds.get(count);
                Contact contact = contactRepository.findById(id)
                        .orElseThrow(() -> new BigContactException("Contact does not exist"));
                Contact newContact = new Contact();
                newContact.setUsername(contact.getUsername());
                newContact.setFirstName(contact.getFirstName());
                newContact.setLastName(contact.getLastName());
                newContact.setUserId(approval.getUserId());
                contactRepository.save(newContact);
            }
        }
        approval.setStatus(status);
        approvalRepository.save(approval);
        ChangeStatusResponseDTO statusResponseDTO = new ChangeStatusResponseDTO();
        statusResponseDTO.setNewStatus(approval.getStatus());
        statusResponseDTO.setUpdatedContactIds(approval.getContactIds());

        return statusResponseDTO;

    }

}
