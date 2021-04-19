package dev.fr13.exceptions;

public class NoSuchOrderException extends NoSuchResourceException {

    public NoSuchOrderException(String orderUuid) {
        super(String.format("Order with uuid %s was not found.", orderUuid));
    }
}
