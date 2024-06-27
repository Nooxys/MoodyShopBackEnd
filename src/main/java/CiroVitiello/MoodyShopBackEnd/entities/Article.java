package CiroVitiello.MoodyShopBackEnd.entities;


import CiroVitiello.MoodyShopBackEnd.enums.ArticleCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    private String description;
    private double price;
    @Enumerated(EnumType.STRING)
    private ArticleCategory category;
    private int quantity;
    private String cover;
    private double averageRating;
    @OneToMany(mappedBy = "article")
    @JsonIgnore
    private List<Review> reviews;


    public Article(String title, String description, double price, ArticleCategory category, int quantity, String cover) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.cover = cover;
        getAverage();
    }

    private void getAverage() {
        this.averageRating = reviews.stream().mapToDouble(review -> review.getRating()).average().orElse(Double.MAX_VALUE);
    }
}
