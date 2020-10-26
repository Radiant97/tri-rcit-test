package com.anastasiyahelda.lambdatest.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.anastasiyahelda.lambdatest.model.Book;
import com.anastasiyahelda.lambdatest.service.BookUploader;
import com.anastasiyahelda.lambdatest.service.impl.BookUploaderImpl;
import com.anastasiyahelda.lambdatest.service.impl.DynamoDbBookRepository;
import com.anastasiyahelda.lambdatest.service.impl.S3StorageService;
import com.anastasiyahelda.lambdatest.service.impl.XmlObjectConvertorImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SaveBookHandler implements RequestStreamHandler {
    private static final String STATUS_CODE = "statusCode";
    private static final String EXCEPTION = "exception";
    private static final String BODY = "body";
    private static final String DYNAMODB_TABLE_NAME = System.getenv("DYNAMODB_TABLE_NAME");
    private static final String S3_BUCKET_NAME = System.getenv("S3_BUCKET_NAME");

    private BookUploader bookUploader;

    public SaveBookHandler() {
        this.bookUploader = new BookUploaderImpl(
                new DynamoDbBookRepository(),
                new XmlObjectConvertorImpl(),
                new S3StorageService()
        );
    }

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            JSONObject responseJson = new JSONObject();

            // Convert the incoming request and upload the body
            try {
                JSONObject event = (JSONObject) new JSONParser().parse(reader);
                String bookXml = (String) event.get(BODY);
                Book book = bookUploader.uploadBook(bookXml, DYNAMODB_TABLE_NAME, S3_BUCKET_NAME);
                responseJson.put(STATUS_CODE, 200);
                responseJson.put(BODY, book.getId());
            } catch (Exception e) {
                responseJson.put(STATUS_CODE, 400);
                responseJson.put(EXCEPTION, e);
            }

            writer.write(responseJson.toString());
        }
    }
}
