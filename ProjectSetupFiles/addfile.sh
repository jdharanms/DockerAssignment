#!/bin/bash

# Set LocalStack endpoint URL
LOCALSTACK_URL="http://s3.us-east-1.localhost.localstack.cloud:4566"

# Bucket and file details
BUCKET_NAME="my-test-bucket"
KEY_NAME="file.json"


# Upload the JSON file to the specified folder in the S3 bucket
echo "Uploading file to S3 bucket..."
awslocal --endpoint-url=$LOCALSTACK_URL s3 cp "$KEY_NAME" s3://$BUCKET_NAME/

# List objects in the bucket to verify upload
awslocal --endpoint-url=$LOCALSTACK_URL s3 ls s3://$BUCKET_NAME

# Check if the upload was successful
if [ $? -eq 0 ]; then
    echo "File uploaded successfully!"
else
    echo "Failed to upload file."
    exit 1
fi
