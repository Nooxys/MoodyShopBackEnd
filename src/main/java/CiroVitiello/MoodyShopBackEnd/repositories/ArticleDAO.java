package CiroVitiello.MoodyShopBackEnd.repositories;

import CiroVitiello.MoodyShopBackEnd.entities.Article;
import CiroVitiello.MoodyShopBackEnd.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleDAO extends JpaRepository<Article, UUID> {
    Page<Article> findAllByUser(User user, Pageable pageable);
}
