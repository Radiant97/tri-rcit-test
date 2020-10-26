package com.anastasiyahelda.lambdatest.exception;

public class StorageExeption extends Exception {
    public StorageExeption() {
        super();
    }

    public StorageExeption(String message) {
        super(message);
    }

    public StorageExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageExeption(Throwable cause) {
        super(cause);
    }
}
