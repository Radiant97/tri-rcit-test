package com.anastasiyahelda.lambdatest.exception;

public class UploaderException extends Exception {
    public UploaderException() {
    }

    public UploaderException(String message) {
        super(message);
    }

    public UploaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploaderException(Throwable cause) {
        super(cause);
    }
}
