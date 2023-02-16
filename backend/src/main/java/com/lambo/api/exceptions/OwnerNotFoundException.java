package com.lambo.api.exceptions;

public class OwnerNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1;

    public OwnerNotFoundException (String message) {
        super(message);
    }
}
