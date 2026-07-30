package kz.com.SerAya.repository; // TODO: adjust to your actual base package

import kz.com.SerAya.entity.JoinRequest;
import kz.com.SerAya.enums.JoinRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Integer> {

    List<JoinRequest> findByUserId(Integer userId);

    List<JoinRequest> findByProgramIdAndStatus(Integer programId, JoinRequestStatus status);

    Optional<JoinRequest> findByUserIdAndProgramIdAndStatus(
            Integer userId, Integer programId, JoinRequestStatus status);

    @Query(value = """
            SELECT *
            FROM join_requests 
            WHERE status = :status
            AND user_id = :ownerId
            
            """, nativeQuery = true)
    List<JoinRequest> findPendingRequestsForOwner(
            @Param("ownerId") Integer ownerId,
            @Param("status") String status);
}