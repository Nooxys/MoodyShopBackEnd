package CiroVitiello.MoodyShopBackEnd.dto;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime dateTimeStamp) {
}
