package dev.fr13.exceptions;

public class NoSuchWorkplaceException extends RuntimeException{

    public NoSuchWorkplaceException(String workplaceUuid) {
        super(String.format("Workplace with uuid %s was not found.", workplaceUuid));
    }
}
