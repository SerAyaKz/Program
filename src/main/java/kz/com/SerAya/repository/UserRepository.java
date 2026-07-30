package kz.com.SerAya.repository;

import kz.com.SerAya.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUid(String uid);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);

    @Query(value = """
    SELECT DISTINCT u.*
    FROM users u
    JOIN user_programs up ON u.id = up.user_id
    JOIN user_programs owner_up ON up.program_id = owner_up.program_id
    WHERE owner_up.user_id = :ownerId
    """, nativeQuery = true)
    List<User> findUsersInOwnersPrograms(Integer ownerId);
}