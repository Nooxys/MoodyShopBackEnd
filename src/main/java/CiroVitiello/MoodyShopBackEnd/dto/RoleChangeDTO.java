package CiroVitiello.MoodyShopBackEnd.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RoleChangeDTO(@NotBlank(message = "Role is required")
                            @Pattern(regexp = "USER|ADMIN", message = "Role must be either USER or ADMIN")
                            String role) {
}
