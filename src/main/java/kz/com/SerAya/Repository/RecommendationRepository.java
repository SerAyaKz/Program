package kz.com.SerAya.Repository;

import kz.com.SerAya.Entity.Recommendation;
import kz.com.SerAya.Entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {
}
