package dev.fr13.exceptions;

public class NoSuchWorkplaceException extends NoSuchResourceException {

    public NoSuchWorkplaceException(String workplaceUuid) {
        super(String.format("Workplace with uuid %s was not found.", workplaceUuid));
    }
}
