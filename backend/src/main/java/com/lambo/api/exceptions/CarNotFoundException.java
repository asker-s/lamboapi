package com.lambo.api.exceptions;

public class CarNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 3;

    public CarNotFoundException (String message) {
        super(message);
    }
}
