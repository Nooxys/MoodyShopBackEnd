package CiroVitiello.MoodyShopBackEnd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;

import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Data

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @OneToOne(mappedBy = "cart")
    @JsonIgnore
    private User user;
    @ManyToMany
    @JoinTable(name = "carts_articles",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> articles;
    private double total;

    public Cart() {

        setTotalPrice();
    }

    public void setTotalPrice() {
        if (articles != null) {
            this.total = articles.stream().mapToDouble(article -> article.getPrice()).sum();
        } else {
            this.total = 0;
        }
    }


}
