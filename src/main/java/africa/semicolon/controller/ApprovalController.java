package africa.semicolon.controller;

import africa.semicolon.contactException.BigContactException;
import africa.semicolon.data.models.Approval;
import africa.semicolon.data.models.Status;
import africa.semicolon.dtos.requests.ShareContactDto;
import africa.semicolon.dtos.response.ApiResponse;
import africa.semicolon.dtos.response.ChangeStatusResponseDTO;
import africa.semicolon.dtos.response.ShareContactDtoResponse;
import africa.semicolon.services.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping("/share")
    public ResponseEntity<?> shareContact(@RequestBody ShareContactDto shareContactDto) {
        try {
            ShareContactDtoResponse result=approvalService.share(shareContactDto);
                return new ResponseEntity<>(new ApiResponse(true,  result),OK);
        } catch (BigContactException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()),BAD_REQUEST);
        }
    }

    @GetMapping("/getApprovals/{userId}")
    public ResponseEntity<?> getApprovals(@PathVariable(name = "userId") String userId) {
        List<Approval> result = approvalService.getApprovals(userId);
        return new ResponseEntity<>(new ApiResponse(true,  result),OK);
    }

    @PostMapping("/changeStatus/{approvalId}/{userId}")
    public ResponseEntity<?> changeStatus(@PathVariable String approvalId, @PathVariable String userId, @RequestParam Status status) {
        try {
            ChangeStatusResponseDTO result  =approvalService.changeStatus(status, approvalId, userId);
            return new ResponseEntity<>(new ApiResponse(true,  result),OK);
        } catch (BigContactException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()),BAD_REQUEST);
        }
    }
}
