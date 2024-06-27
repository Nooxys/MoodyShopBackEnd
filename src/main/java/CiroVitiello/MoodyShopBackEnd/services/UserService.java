package CiroVitiello.MoodyShopBackEnd.services;


import CiroVitiello.MoodyShopBackEnd.dto.ChangePasswordDTO;
import CiroVitiello.MoodyShopBackEnd.dto.NewUserDTO;
import CiroVitiello.MoodyShopBackEnd.dto.UpdateUserDTO;
import CiroVitiello.MoodyShopBackEnd.entities.Cart;
import CiroVitiello.MoodyShopBackEnd.entities.User;
import CiroVitiello.MoodyShopBackEnd.enums.UserRole;
import CiroVitiello.MoodyShopBackEnd.exceptions.BadRequestException;
import CiroVitiello.MoodyShopBackEnd.exceptions.NotFoundException;
import CiroVitiello.MoodyShopBackEnd.repositories.UserDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO ud;
    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary cloudinaryUploader;

//    @Autowired
//    private CartService cs;


    public Page<User> getUsers(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ud.findAll(pageable);
    }

    public User save(NewUserDTO body) {
        this.ud.findByEmail(body.email())
                .ifPresent(user -> {
                    throw new BadRequestException(" email " + user.getEmail() + " already in use!");
                });

        this.ud.findByUsername(body.username())
                .ifPresent(user -> {
                    throw new BadRequestException(" username " + user.getUsername() + " already  in use!");
                });
        User newUser = new User(body.name(), body.surname(), body.username(), body.email(), bcrypt.encode(body.password()), body.birthDate());
//        Cart newCart = new Cart(newUser);
        return this.ud.save(newUser);
    }

    public User findById(UUID id) {
        return this.ud.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIdAndUpdate(UUID id, UpdateUserDTO body) {
        User found = this.findById(id);
        if (found.getEmail().equals(body.email()) && found.getUsername().equals(body.username())) {
            found.setName(body.name());
            found.setSurname(body.surname());
            found.setBirthDate(body.birthDate());
            if (!found.getAvatar().contains("cloudinary")) found.setTemporaryAvatar();
        }
        if (found.getEmail().equals(body.email()) && !found.getUsername().equals(body.username())) {
            this.ud.findByUsername(body.username())
                    .ifPresent(user -> {
                        throw new BadRequestException(" username " + user.getUsername() + " already  in use!");
                    });
            found.setName(body.name());
            found.setSurname(body.surname());
            found.setBirthDate(body.birthDate());
            found.setUsername(body.username());
            if (!found.getAvatar().contains("cloudinary")) found.setTemporaryAvatar();
        }
        if (found.getUsername().equals(body.username()) && !found.getEmail().equals(body.email())) {
            this.ud.findByEmail(body.email()).ifPresent(user -> {
                throw new BadRequestException(" email " + user.getEmail() + " already  in use!");
            });
            found.setName(body.name());
            found.setSurname(body.surname());
            found.setBirthDate(body.birthDate());
            found.setEmail(body.email());
            if (!found.getAvatar().contains("cloudinary")) found.setTemporaryAvatar();
        }
        if (!found.getUsername().equals(body.username()) && !found.getEmail().equals(body.email())) {
            if (this.ud.existsByUsernameAndEmail(body.username(), body.email()))
                throw new BadRequestException("Username and email are already in use!");
            this.ud.findByEmail(body.email())
                    .ifPresent(user -> {
                        throw new BadRequestException(" email " + user.getEmail() + " already in use!");
                    });

            this.ud.findByUsername(body.username())
                    .ifPresent(user -> {
                        throw new BadRequestException(" username " + user.getUsername() + " already  in use!");
                    });

            found.setBirthDate(body.birthDate());
            found.setName(body.name());
            found.setSurname(body.surname());
            found.setEmail(body.email());
            found.setUsername(body.username());
            if (!found.getAvatar().contains("cloudinary")) found.setTemporaryAvatar();
        }
        this.ud.save(found);
        return found;
    }

    public void findByIdAndDelete(UUID id) {
        User foundUser = this.findById(id);
//        Cart foundCart = this.cs.findById(foundUser.getCart().getId());
//        this.cs.delete(foundCart.getId());
        this.ud.delete(foundUser);
    }

    public User uploadImage(MultipartFile image, UUID userId) throws IOException {
        User found = findById(userId);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        ud.save(found);
        return found;
    }

    public User findByEmail(String email) {
        return ud.findByEmail(email).orElseThrow(() -> new NotFoundException("User with " + email + " not found!"));
    }

    public User updatePassword(UUID id, ChangePasswordDTO psw) {
        User found = this.findById(id);
        if (psw.newPassword().equals(psw.oldPassword())) {
            throw new BadRequestException("The new password cannot be the same as the old one");
        }
        if (!bcrypt.matches(psw.oldPassword(), found.getPassword())) {
            throw new BadRequestException("The old password is incorrect");
        }
        String password = bcrypt.encode(psw.newPassword());
        found.setPassword(password);
        return ud.save(found);
    }

    public User updateRole(UUID id, UserRole role) {
        User found = this.findById(id);
        found.setRole(role);
        return this.ud.save(found);
    }

    public List<User> findAdmins() {
        return ud.findByRole(UserRole.ADMIN);
    }
}
