package CiroVitiello.MoodyShopBackEnd.controllers;

import CiroVitiello.MoodyShopBackEnd.dto.NewUserDTO;
import CiroVitiello.MoodyShopBackEnd.dto.UserLoginDTO;
import CiroVitiello.MoodyShopBackEnd.dto.UserLoginResponseDTO;
import CiroVitiello.MoodyShopBackEnd.entities.Cart;
import CiroVitiello.MoodyShopBackEnd.entities.User;
import CiroVitiello.MoodyShopBackEnd.exceptions.BadRequestException;
import CiroVitiello.MoodyShopBackEnd.services.AuthService;
import CiroVitiello.MoodyShopBackEnd.services.CartService;
import CiroVitiello.MoodyShopBackEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService us;

    @Autowired
    private AuthService as;

    @Autowired
    private CartService cs;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        return new UserLoginResponseDTO(this.as.authenticateUsersAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return us.save(body);

    }
}
