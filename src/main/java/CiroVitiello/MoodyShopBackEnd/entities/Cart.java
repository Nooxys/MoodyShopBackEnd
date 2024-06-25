package CiroVitiello.MoodyShopBackEnd.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(name = "carts_articles",
    joinColumns = @JoinColumn(name = "cart_id"),
    inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> articles;
    private double total;

    public Cart(double total) {
        this.total = total;
    }

    public void setTotalPrice() {
       this.total = articles.stream().mapToDouble(article -> article.getPrice()).sum();
    }
}
