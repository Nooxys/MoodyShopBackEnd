package CiroVitiello.MoodyShopBackEnd.controllers;

import CiroVitiello.MoodyShopBackEnd.entities.Article;
import CiroVitiello.MoodyShopBackEnd.entities.Review;
import CiroVitiello.MoodyShopBackEnd.services.ArticleService;
import CiroVitiello.MoodyShopBackEnd.services.ReviewService;
import CiroVitiello.MoodyShopBackEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/general")
public class GeneralController {

    @Autowired
    private ReviewService rs;

    @Autowired
    private UserService us;

    @Autowired
    private ArticleService as;

    @GetMapping("/articles/{artId}")
    public Article findSubById(@PathVariable UUID artId) {
        return as.findById(artId);
    }

    @GetMapping("/articles")
    public Page<Article> getAllSubs(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.as.getArticles(page, size, sortBy);
    }

    @GetMapping("/reviews")
    public Page<Review> getAllReviews(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return this.rs.getReviews(page, size, sortBy);
    }
}
