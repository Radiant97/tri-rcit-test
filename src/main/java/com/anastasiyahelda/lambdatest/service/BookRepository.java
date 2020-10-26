package com.anastasiyahelda.lambdatest.service;

import com.anastasiyahelda.lambdatest.model.Book;

public interface BookRepository {
    Book save(String tableName, Book book);
}
