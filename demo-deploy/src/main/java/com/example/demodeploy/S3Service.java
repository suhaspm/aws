package com.example.demodeploy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Service
public class S3Service {
    private final S3Client s3;

    public S3Service(@Value("${aws.region}") String region) {
        this.s3 = S3Client.builder()
                .region(Region.of(region)) // Replace with your region
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public String uploadDummyFile(String bucketName, String key) {
        String content = "Deployed at: " + Instant.now();

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3.putObject(putRequest, RequestBody.fromBytes(content.getBytes(StandardCharsets.UTF_8)));

        return "https://s3.eu-central-1.amazonaws.com/" + bucketName + "/" + key;
    }

    public String uploadFile(MultipartFile file, String deploymentId) throws IOException {
    String fileName = deploymentId + "_" + file.getOriginalFilename();
    String bucketName = "lambda-upload-trigger-dev"; 

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .contentType(file.getContentType())
            .build();

    s3.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

    // Manually construct the public S3 URL
    return "https://" + bucketName + ".s3." + "eu-central-1" + ".amazonaws.com/" + fileName;

}

}
