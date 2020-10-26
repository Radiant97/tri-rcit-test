package com.anastasiyahelda.lambdatest.service;

import com.anastasiyahelda.lambdatest.exception.StorageExeption;

import java.io.InputStream;

public interface StorageService {
    void upload(String path, String fileName, InputStream stream) throws StorageExeption;
}
