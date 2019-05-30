package com.sayan.easylistwidget.exceptions;

public class IllegalModelFoundException extends RuntimeException {
    public IllegalModelFoundException() {
        super("No method found in the model");
    }

    public IllegalModelFoundException(String message) {
        super(message);
    }
}
