package CiroVitiello.MoodyShopBackEnd.dto;

import CiroVitiello.MoodyShopBackEnd.enums.ArticleCategory;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public record NewArticleDTO(@NotEmpty(message = "title is required!")
                            @Size(min = 2, max = 20, message = " a title must be  between 2 and 20 characters!")
                            String title,
                            @NotEmpty(message = "description is required!")
                            @Size(min = 3, max = 500, message = " a description must be  between 3 and 500 characters!")
                            String description,
                            @NotNull(message = "price is required!")
                            double price,
                            @NotBlank(message = "a category is required")
                            @Pattern(regexp = "HOME|KIDS|ELECTRONICS|VIDEOGAMES|BEAUTY|BOOKS|MUSIC",
                                    message = "Category must be either HOME, KIDS, ELECTRONICS, VIDEOGAMES, BEAUTY, BOOKS or MUSIC")
                            ArticleCategory category,
                            @NotNull(message = "a quantity is required!")
                            int quantity,
                            @NotEmpty(message = "a cover is required!")
                            MultipartFile cover
) {
}
