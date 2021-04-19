package dev.fr13.exceptions;

public class NoSuchClientException extends NoSuchResourceException {

    public NoSuchClientException(String clientUuid) {
        super(String.format("Client with uuid %s was not found.", clientUuid));
    }
}
