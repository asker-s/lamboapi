package com.lambo.api.exceptions;

public class GarageNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2;

    public GarageNotFoundException (String message) {
        super(message);
    }
}
