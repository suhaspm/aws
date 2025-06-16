package com.example.demodeploy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamoDBService {
    private final DynamoDbClient dynamoDb;

    public DynamoDBService(@Value("${aws.region}") String region) {
        this.dynamoDb = DynamoDbClient.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public void saveDeploymentMetadata(String tableName, String deploymentId, String s3Url) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("filename", AttributeValue.fromS(deploymentId + ".txt")); // partition key
        item.put("s3Url", AttributeValue.fromS(s3Url));
        item.put("timestamp", AttributeValue.fromS(Instant.now().toString()));

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        dynamoDb.putItem(request);
    }

    public List<DeploymentMetadata> getAllDeployments(String tableName) {
        ScanRequest scanRequest = ScanRequest.builder().tableName(tableName).build();
        ScanResponse scanResponse = dynamoDb.scan(scanRequest);
        List<DeploymentMetadata> deployments = new ArrayList<>();
         for (Map<String, AttributeValue> item : scanResponse.items()) {
            String filename = null;
            String bucket = null;
            String uploadTime = null; // Declare uploadTime

            // Extract 'filename'
            if (item.containsKey("filename") && item.get("filename").s() != null) {
                filename = item.get("filename").s();
            }

            // Extract 'bucket'
            if (item.containsKey("bucket") && item.get("bucket").s() != null) {
                bucket = item.get("bucket").s();
            }

            // Extract 'upload_time' (important to use the correct key and type)
            if (item.containsKey("upload_time") && item.get("upload_time").s() != null) {
                uploadTime = item.get("upload_time").s();
            }

            // Create a DeploymentMetadata object with the retrieved values
            deployments.add(new DeploymentMetadata(filename, bucket, uploadTime));
        }
        return deployments;
    }
}
