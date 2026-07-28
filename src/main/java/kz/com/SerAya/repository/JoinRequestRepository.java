package kz.com.SerAya.repository; // TODO: adjust to your actual base package

import kz.com.SerAya.dto.JoinRequest;
import kz.com.SerAya.enums.JoinRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Integer> {

    List<JoinRequest> findByUserId(Integer userId);

    List<JoinRequest> findByProgramIdAndStatus(Integer programId, JoinRequestStatus status);

    Optional<JoinRequest> findByUserIdAndProgramIdAndStatus(
            Integer userId, Integer programId, JoinRequestStatus status);
}