package com.anastasiyahelda.lambdatest.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.anastasiyahelda.lambdatest.exception.StorageExeption;
import com.anastasiyahelda.lambdatest.service.StorageService;

import java.io.InputStream;

public class S3StorageService implements StorageService {

    private static final String PLAIN_TEXT = "plain/text";
    private AmazonS3 s3Client;

    public S3StorageService() {
        this.s3Client = AmazonS3ClientBuilder.defaultClient();
    }

    @Override
    public void upload(String bucketName, String fileName, InputStream stream) throws StorageExeption {
        this.createBucketIfAbsent(bucketName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(PLAIN_TEXT);
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, stream, metadata);

        s3Client.putObject(request);
    }

    private Bucket createBucketIfAbsent(String bucketName) throws StorageExeption{
        Bucket bucket = null;
        if (!s3Client.doesBucketExistV2(bucketName)) {
            try {
                bucket = s3Client.createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                throw new StorageExeption("An exception occurred during creating s3 bucket", e);
            }
        }

        return bucket;
    }
}
