package com.example.demodeploy;

import com.example.demodeploy.DynamoDBService;
import com.example.demodeploy.S3Service;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DeploymentController {

    private final S3Service s3Service;
    private final DynamoDBService dynamoDBService;

    public DeploymentController(S3Service s3Service, DynamoDBService dynamoDBService) {
        this.s3Service = s3Service;
        this.dynamoDBService = dynamoDBService;
    }

    @PostMapping("/create-deployment")
    public String createDeployment() {
        String bucketName = "lambda-upload-trigger-dev";     // Replace with your actual bucket
        String tableName = "FileMetadataTable";        // Replace with your actual table
        String deploymentId = UUID.randomUUID().toString();
        String key = deploymentId + ".txt";

        String s3Url = s3Service.uploadDummyFile(bucketName, key);
        dynamoDBService.saveDeploymentMetadata(tableName, deploymentId, s3Url);

        return "Deployment Created. File URL: " + s3Url;
    }
}
