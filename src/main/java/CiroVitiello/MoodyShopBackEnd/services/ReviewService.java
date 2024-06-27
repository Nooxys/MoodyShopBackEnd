package CiroVitiello.MoodyShopBackEnd.services;

import CiroVitiello.MoodyShopBackEnd.dto.NewReviewDTO;
import CiroVitiello.MoodyShopBackEnd.entities.Review;
import CiroVitiello.MoodyShopBackEnd.exceptions.NotFoundException;
import CiroVitiello.MoodyShopBackEnd.repositories.ReviewDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    @Autowired
    private ReviewDAO rd;

    @Autowired
    private UserService us;

    @Autowired
    private ArticleService as;

    public Page<Review> getReviews(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.rd.findAll(pageable);
    }

    public Review save(NewReviewDTO body, UUID userId, UUID articleId) {
        Review newReview = new Review(body.title(), body.description(), body.rating(), us.findById(userId), as.findById(articleId));
        return this.rd.save(newReview);
    }

    public Review findById(UUID uuid) {
        return this.rd.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    public List<Review> findByUserId(UUID userId) {
        return this.rd.findByUserId(userId);
    }

    public Review findByIdAndUpdate(UUID uuid, NewReviewDTO body) {
        Review found = this.findById(uuid);
        found.setTitle(body.title());
        found.setDescription(body.description());
        found.setRating(body.rating());
        this.rd.save(found);
        return found;
    }

    public void findByIdAndDelete(UUID uuid) {
        Review found = this.findById(uuid);
        this.rd.delete(found);
    }
}
