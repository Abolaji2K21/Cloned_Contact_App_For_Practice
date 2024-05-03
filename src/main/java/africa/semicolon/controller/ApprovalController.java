package africa.semicolon.controller;

import africa.semicolon.contactException.BigContactException;
import africa.semicolon.data.models.Approval;
import africa.semicolon.data.models.Status;
import africa.semicolon.dtos.requests.ShareContactDto;
import africa.semicolon.dtos.response.ApiResponse;
import africa.semicolon.services.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping("/share")
    public ResponseEntity<?> shareContact(@RequestBody ShareContactDto shareContactDto) {
        try {
            approvalService.share(shareContactDto);
                return new ResponseEntity<>(new ApiResponse(true,  result),OK);
        } catch (BigContactException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getApprovals/{userId}")
    public ResponseEntity<?> getApprovals(@PathVariable String userId) {
        List<Approval> approvals = approvalService.getApprovals(userId);
        return ResponseEntity.ok(approvals);
    }

    @PostMapping("/changeStatus/{approvalId}/{userId}")
    public ResponseEntity<?> changeStatus(@PathVariable String approvalId, @PathVariable String userId, @RequestParam Status status) {
        try {
            approvalService.changeStatus(status, approvalId, userId);
            return ResponseEntity.ok("Status changed successfully");
        } catch (BigContactException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
