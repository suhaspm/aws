package com.example.demodeploy;

public class DeploymentMetadata {
    private String filename; // Matches DynamoDB 'filename'
    private String bucket;   // Matches DynamoDB 'bucket'
    private String uploadTime; // Matches DynamoDB 'upload_time'

    // Constructors
    public DeploymentMetadata() {}

    public DeploymentMetadata(String filename, String bucket, String uploadTime) {
        this.filename = filename;
        this.bucket = bucket;
        this.uploadTime = uploadTime;
    }

    // Getters and Setters (REQUIRED for Jackson serialization)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    // You might consider adding a getFileUrl() method that combines bucket and filename if needed on frontend
    public String getFileUrl() {
        // This would create a URL based on your S3 bucket and filename
        // Example: "https://" + bucket + ".s3.amazonaws.com/" + filename;
        // You might need to adjust region or other S3 specific details
        // For now, let's keep it simple or remove if not immediately needed
        return "s3://" + bucket + "/" + filename;
    }
}