package CiroVitiello.MoodyShopBackEnd.services;

import CiroVitiello.MoodyShopBackEnd.dto.UserLoginDTO;
import CiroVitiello.MoodyShopBackEnd.entities.User;
import CiroVitiello.MoodyShopBackEnd.exceptions.UnauthorizedException;
import CiroVitiello.MoodyShopBackEnd.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService us;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jt;

    public String authenticateUsersAndGenerateToken(UserLoginDTO body) {
        User user = this.us.findByEmail(body.email());
        if ((bcrypt.matches(body.password(), user.getPassword()))) {
            return jt.createToken(user);

        } else {
            throw new UnauthorizedException("Invalid credentials! Please log in again!");
        }
    }
}
