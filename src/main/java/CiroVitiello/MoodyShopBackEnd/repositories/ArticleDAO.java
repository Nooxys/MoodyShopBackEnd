package CiroVitiello.MoodyShopBackEnd.repositories;

import CiroVitiello.MoodyShopBackEnd.entities.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleDAO extends JpaRepository<Article, UUID> {

}
