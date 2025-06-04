package com.example.demodeploy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.Instant;
import java.util.HashMap;
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
}
