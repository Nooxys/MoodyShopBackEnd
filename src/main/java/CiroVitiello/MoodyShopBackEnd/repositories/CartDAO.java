package CiroVitiello.MoodyShopBackEnd.repositories;

import CiroVitiello.MoodyShopBackEnd.entities.Cart;
import CiroVitiello.MoodyShopBackEnd.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartDAO extends JpaRepository<Cart, UUID> {
    Cart findByUser(User user);
}
