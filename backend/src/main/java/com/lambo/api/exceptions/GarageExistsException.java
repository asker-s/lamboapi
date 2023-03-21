package com.lambo.api.exceptions;

public class GarageExistsException extends RuntimeException{
    private static final long serialVersionUID = 4;

    public GarageExistsException (String message) {
        super(message);
    }
}
