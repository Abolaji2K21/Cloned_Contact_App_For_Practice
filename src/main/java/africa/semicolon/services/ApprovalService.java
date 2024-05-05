package africa.semicolon.services;

import africa.semicolon.data.models.Approval;
import africa.semicolon.data.models.Status;
import africa.semicolon.dtos.requests.ChangeStatusRequestDTO;
import africa.semicolon.dtos.requests.ShareContactDto;
import africa.semicolon.dtos.response.ChangeStatusResponseDTO;
import africa.semicolon.dtos.response.ShareContactDtoResponse;

import java.util.List;

public interface ApprovalService {

    ShareContactDtoResponse share(ShareContactDto shareContactDto);
    List<Approval> getApprovals(String userId);

    ChangeStatusResponseDTO changeStatus(ChangeStatusRequestDTO request);

}
