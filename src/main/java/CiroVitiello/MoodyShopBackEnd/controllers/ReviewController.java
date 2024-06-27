package CiroVitiello.MoodyShopBackEnd.controllers;

import CiroVitiello.MoodyShopBackEnd.dto.NewReviewDTO;
import CiroVitiello.MoodyShopBackEnd.entities.Article;
import CiroVitiello.MoodyShopBackEnd.entities.Review;
import CiroVitiello.MoodyShopBackEnd.entities.User;
import CiroVitiello.MoodyShopBackEnd.exceptions.BadRequestException;
import CiroVitiello.MoodyShopBackEnd.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService rs;


    @GetMapping("/me")
    public List<Review> getMyReviews(@AuthenticationPrincipal User currentUser) {
        return this.rs.findByUserId(currentUser.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review save(@RequestBody @Validated NewReviewDTO body, BindingResult validation, @AuthenticationPrincipal User currentUser, Article article) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.rs.save(body, currentUser.getId(), article.getId());
    }

    @PutMapping("/{reviewId}")
    public Review findByIdAndUpdate(@PathVariable UUID reviewId,
                                    @RequestBody @Validated NewReviewDTO body,
                                    BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        return this.rs.findByIdAndUpdate(reviewId, body);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID reviewId) {
        this.rs.findByIdAndDelete(reviewId);
    }
}
