package dev.fr13.exceptions;

public class NoSuchResourceException extends RuntimeException {

    public NoSuchResourceException(String message) {
        super(message);
    }
}
