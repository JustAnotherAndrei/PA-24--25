package com.myapp.imageapp.exception;

public class ImageAppException extends Exception {
    public ImageAppException(String message) {
        super(message);
    }

    public ImageAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
