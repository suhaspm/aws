package com.example.demodeploy;

import com.example.demodeploy.DynamoDBService;
import com.example.demodeploy.S3Service;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
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

    @GetMapping("/deployments")
    public List<DeploymentMetadata> getAllDeployments(){
        String tableName = "FileMetadataTable";
        return dynamoDBService.getAllDeployments(tableName);
    }

    @PostMapping("/upload")
public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
        String deploymentId = UUID.randomUUID().toString();
        String fileUrl = s3Service.uploadFile(file, deploymentId); // method you'll define below
        dynamoDBService.saveDeploymentMetadata("FileMetadataTable", deploymentId, fileUrl);
        return ResponseEntity.ok("File uploaded successfully with ID: " + deploymentId);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
    }
}

}
