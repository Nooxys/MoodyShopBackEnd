package CiroVitiello.MoodyShopBackEnd.repositories;

import CiroVitiello.MoodyShopBackEnd.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewDAO extends JpaRepository<Review, UUID> {
    List<Review> findByUserId(UUID userId);
}
