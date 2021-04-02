package dev.fr13.exceptions;

public class NoSuchClientException extends RuntimeException{

    public NoSuchClientException(String workplaceUuid) {
        super(String.format("Client with uuid %s was not found.", workplaceUuid));
    }
}
