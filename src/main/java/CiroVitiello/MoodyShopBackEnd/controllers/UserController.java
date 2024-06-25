package CiroVitiello.MoodyShopBackEnd.controllers;



import CiroVitiello.MoodyShopBackEnd.dto.ChangePasswordDTO;
import CiroVitiello.MoodyShopBackEnd.dto.ChangePasswordResponseDTO;
import CiroVitiello.MoodyShopBackEnd.dto.RoleChangeDTO;

import CiroVitiello.MoodyShopBackEnd.dto.UpdateUserDTO;
import CiroVitiello.MoodyShopBackEnd.entities.User;
import CiroVitiello.MoodyShopBackEnd.enums.UserRole;
import CiroVitiello.MoodyShopBackEnd.exceptions.BadRequestException;
import CiroVitiello.MoodyShopBackEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService us;

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentUser, @RequestBody @Validated UpdateUserDTO updatedUser, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.us.findByIdAndUpdate(currentUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentUser) {
        this.us.findByIdAndDelete(currentUser.getId());
    }

    @PostMapping("/me/upload")
    public User uploadAvatar(@RequestParam("avatar") MultipartFile image, @AuthenticationPrincipal User currentUser) throws IOException {
        return us.uploadImage(image, currentUser.getId());
    }

    @PutMapping("/me/password")
    public ChangePasswordResponseDTO updateMePassword(@AuthenticationPrincipal User user, @RequestBody @Validated ChangePasswordDTO psw, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        this.us.updatePassword(user.getId(), psw);
        return new ChangePasswordResponseDTO("password changed!");
    }


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.us.getUsers(page, size, sortBy);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findUserById(@PathVariable UUID userId) {
        return us.findById(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findUserByIdAndUpdate(@PathVariable UUID userId, @RequestBody @Validated UpdateUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return us.findByIdAndUpdate(userId, body);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findUserByIdAndDelete(@PathVariable UUID userId) {
        us.findByIdAndDelete(userId);
    }

    @PostMapping("/upload/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User uploadAvatar(@RequestParam("avatar") MultipartFile image, @PathVariable UUID userId) throws IOException {
        return this.us.uploadImage(image, userId);
    }

    @PutMapping("/password/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUserPassword(@PathVariable UUID userId, @RequestBody @Validated ChangePasswordDTO userPassword, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return us.updatePassword(userId, userPassword);
    }

    @PutMapping("/role/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User uploadUserRole(@PathVariable UUID userId, @RequestBody @Validated RoleChangeDTO role, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.us.updateRole(userId, UserRole.valueOf(role.role()));
    }


}
