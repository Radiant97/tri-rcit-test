package com.anastasiyahelda.lambdatest.service.impl;

import com.anastasiyahelda.lambdatest.exception.ObjectConvertorException;
import com.anastasiyahelda.lambdatest.exception.StorageExeption;
import com.anastasiyahelda.lambdatest.exception.UploaderException;
import com.anastasiyahelda.lambdatest.model.Book;
import com.anastasiyahelda.lambdatest.service.BookRepository;
import com.anastasiyahelda.lambdatest.service.BookUploader;
import com.anastasiyahelda.lambdatest.service.ObjectConvertor;
import com.anastasiyahelda.lambdatest.service.StorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class BookUploaderImpl implements BookUploader {

    private static final String BOOK_NAME_FORMAT = "book-%s.txt";
    private static final String STORAGE_EXCEPTION_FORMAT = "An exception during storing book with id: %s";

    private BookRepository bookRepository;
    private ObjectConvertor objectConvertor;
    private StorageService storageService;

    public BookUploaderImpl(BookRepository bookRepository, ObjectConvertor objectConvertor, StorageService storageService) {
        this.bookRepository = bookRepository;
        this.objectConvertor = objectConvertor;
        this.storageService = storageService;
    }

    public Book uploadBook(String bookString, String tableName, String storageName) throws UploaderException, IOException {
        Book book = null;
        if (isPresent(bookString)) {
            try {
                book = objectConvertor.convertStringToObject(bookString, Book.class);
            } catch (ObjectConvertorException e) {
                throw new UploaderException("An exception during converting string to object", e);
            }

            // Title shouldn't be empty
            if (isPresent(book.getTitle())) {
                book.setId(UUID.randomUUID().toString());

                book = bookRepository.save(tableName, book);

                try(InputStream stream = new ByteArrayInputStream(bookString.getBytes())) {
                    // Add id to identify the book in a storage
                    storageService.upload(storageName, String.format(BOOK_NAME_FORMAT, book.getId()), stream);
                } catch (StorageExeption e) {
                    throw new UploaderException(String.format(STORAGE_EXCEPTION_FORMAT, book.getId()), e);
                }
            }
        }

        return book;
    }

    private boolean isPresent(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
