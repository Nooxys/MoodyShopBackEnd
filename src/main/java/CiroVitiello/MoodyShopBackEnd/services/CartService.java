package CiroVitiello.MoodyShopBackEnd.services;

import CiroVitiello.MoodyShopBackEnd.entities.Article;
import CiroVitiello.MoodyShopBackEnd.entities.Cart;
import CiroVitiello.MoodyShopBackEnd.entities.User;
import CiroVitiello.MoodyShopBackEnd.exceptions.NotFoundException;
import CiroVitiello.MoodyShopBackEnd.repositories.CartDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private CartDAO cd;
    @Autowired
    private UserService us;

    @Autowired
    private ArticleService as;


    public Cart findById(UUID uuid) {
        return this.cd.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    public Cart save() {
        Cart cart = new Cart();
        return this.cd.save(cart);
    }

    public void delete(UUID uuid) {
        Cart found = this.findById(uuid);
        this.cd.delete(found);
    }

    public Cart removeArticleFromCart(UUID cartId, UUID articleId) {
        Article art = as.findById(articleId);
        Cart cart = this.findById(cartId);
        cart.getArticles().remove(art);
        art.setQuantity(+1);
        cart.setTotalPrice();
        return this.cd.save(cart);
    }

    public Cart AddArticleToCart(UUID cartId, UUID articleId) {
        Article art = as.findById(articleId);
        Cart cart = this.findById(cartId);
        cart.getArticles().remove(art);
        art.setQuantity(-1);
        cart.setTotalPrice();
        return this.cd.save(cart);
    }

    public Cart getCartByUser(UUID userId) {
        User found = this.us.findById(userId);
        return this.cd.findByUser(found);
    }

    
}
