package com.anastasiyahelda.lambdatest.exception;

public class ObjectConvertorException extends Exception {
    public ObjectConvertorException() {
        super();
    }

    public ObjectConvertorException(String message) {
        super(message);
    }

    public ObjectConvertorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectConvertorException(Throwable cause) {
        super(cause);
    }
}
