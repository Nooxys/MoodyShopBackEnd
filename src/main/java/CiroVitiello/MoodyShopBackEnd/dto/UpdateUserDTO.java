package CiroVitiello.MoodyShopBackEnd.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserDTO(@NotEmpty(message = "a Name is required!")
                            @Size(min = 2, max = 10, message = " your name must be  between 2 and 10 characters!")
                            String name,
                            @NotEmpty(message = "a Surname is required!")
                            @Size(min = 2, max = 15, message = " your surname must be  between 2 and 15 characters!")
                            String surname,
                            @NotEmpty(message = "an Username is required!")
                            @Size(min = 3, max = 12, message = " your username must be  between 3 and 12 characters!")
                            String username,
                            @NotEmpty(message = "email is required!")
                            @Email(message = "please check your email format!")
                            String email,
                            @NotNull(message = "a birth date is required!")
                            LocalDate birthDate) {
}
