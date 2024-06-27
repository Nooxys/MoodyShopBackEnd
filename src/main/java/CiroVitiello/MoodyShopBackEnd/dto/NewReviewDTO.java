package CiroVitiello.MoodyShopBackEnd.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewReviewDTO(@NotEmpty(message = "title is required!")
                           @Size(max = 50, message = "the title must be max 50 characters")
                           String title,
                           @NotEmpty(message = "description is required")
                           @Size(max = 300, message = "the description must be max 300 characters")
                           String description,
                           @NotNull(message = "a rating is required!")
                           int rating) {
}
