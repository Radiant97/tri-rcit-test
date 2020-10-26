package com.anastasiyahelda.lambdatest.service;

import com.anastasiyahelda.lambdatest.exception.UploaderException;
import com.anastasiyahelda.lambdatest.model.Book;

import java.io.IOException;

public interface BookUploader {
    Book uploadBook(String bookString, String tableName, String storageName) throws UploaderException, IOException;
}
