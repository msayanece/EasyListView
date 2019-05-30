package com.sayan.easylistwidget.exceptions;

public class InvalidSetupException extends RuntimeException {

    public InvalidSetupException() {
        super("Invalid setup, please check the documentation for more information");
    }

    public InvalidSetupException(String message) {
        super(message);
    }
}
