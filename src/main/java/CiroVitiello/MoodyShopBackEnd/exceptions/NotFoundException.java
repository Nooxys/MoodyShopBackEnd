package CiroVitiello.MoodyShopBackEnd.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException(UUID id) {
        super("element with " + id + " not found");

    }

    public NotFoundException(String message) {
        super(message);
    }
}