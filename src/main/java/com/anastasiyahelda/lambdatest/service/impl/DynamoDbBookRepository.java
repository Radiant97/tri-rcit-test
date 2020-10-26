package com.anastasiyahelda.lambdatest.service.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.anastasiyahelda.lambdatest.model.Book;
import com.anastasiyahelda.lambdatest.service.BookRepository;

public class DynamoDbBookRepository implements BookRepository {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String EMPTY_STRING = "";

    private DynamoDB dynamoDBClient;

    public DynamoDbBookRepository() {
        this.dynamoDBClient = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public Book save(String tableName, Book book) {
        // TODO: Create table if it does not exist

        dynamoDBClient.getTable(tableName)
                .putItem(new PutItemSpec().withItem(
                        new Item()
                                .withString(ID, book.getId())
                                .withString(TITLE, book.getTitle())
                                .withString(AUTHOR, book.getAuthor() != null ? book.getAuthor() : EMPTY_STRING)));

        return book;
    }
}
