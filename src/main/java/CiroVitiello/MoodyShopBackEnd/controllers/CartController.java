package CiroVitiello.MoodyShopBackEnd.controllers;

import CiroVitiello.MoodyShopBackEnd.entities.Cart;
import CiroVitiello.MoodyShopBackEnd.entities.User;
import CiroVitiello.MoodyShopBackEnd.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cs;

    @GetMapping("/me")
    public Cart getMyCart(
            @AuthenticationPrincipal User currentUser) {
        return this.cs.getCartByUser(currentUser.getId());
    }

    @PutMapping("{cartId}/articles/{articleId}")
    public Cart addArticleToCart(@PathVariable UUID cartId, @PathVariable UUID articleId) {
        return this.cs.AddArticleToCart(cartId, articleId);
    }

    @DeleteMapping("{cartId}/articles/{articleId}")
    public Cart removeArticleFromCart(@PathVariable UUID cartId, @PathVariable UUID articleId) {
        return this.cs.removeArticleFromCart(cartId, articleId);
    }
}
