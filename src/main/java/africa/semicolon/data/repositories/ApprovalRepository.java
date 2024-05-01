package africa.semicolon.data.repositories;

import africa.semicolon.data.models.Approval;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ApprovalRepository extends MongoRepository<Approval, String> {
    Optional<Approval> findByApprovalIdAndUserId(String approvalId, String userId);

    List<Approval> findByUserId(String userId);
}
