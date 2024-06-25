package CiroVitiello.MoodyShopBackEnd.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordDTO(@NotBlank(message = "Password is required")
                                @Size(min = 4, message = "old Password must be at least 4 characters long")
                                String oldPassword,
                                @NotBlank(message = "Password is required")
                                @Size(min = 4, message = "new Password must be at least 4 characters long")
                                String newPassword) {
}
