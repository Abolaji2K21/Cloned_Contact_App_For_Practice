package africa.semicolon.services;

import africa.semicolon.data.models.Approval;
import africa.semicolon.data.models.Status;
import africa.semicolon.dtos.requests.ShareContactDto;

import java.util.List;

public interface ApprovalService {

    void share(ShareContactDto shareContactDto);
    List<Approval> getApprovals(String userId);

    void changeStatus(Status status, String approvalId, String userId);


}
