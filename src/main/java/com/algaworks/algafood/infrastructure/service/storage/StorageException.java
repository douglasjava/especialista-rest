package com.algaworks.algafood.infrastructure.service.storage;

public class StorageException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
