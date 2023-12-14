package com.v1.opensquad.service.exception;

public class AuthDataException extends RuntimeException {

    private static final long serialVersionUID = 1355659272501706296L;
    private final String description;

    public AuthDataException(final String description){
        super(description);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
