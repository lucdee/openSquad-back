package com.v1.opensquad.service.exception;

public class ProfileDataException extends RuntimeException {

    private static final long serialVersionUID = 1355659272501706296L;
    private final String description;

    public ProfileDataException(final String description){
        super(description);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
