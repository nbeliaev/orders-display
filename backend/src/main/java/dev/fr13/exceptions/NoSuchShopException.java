package dev.fr13.exceptions;

public class NoSuchShopException extends RuntimeException{

    public NoSuchShopException(String shopUuid) {
        super(String.format("Shop with uuid %s was not found.", shopUuid));
    }
}
