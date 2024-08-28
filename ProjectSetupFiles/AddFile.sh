#!/bin/bash


LOCALSTACK_URL="http://s3.us-east-1.localhost.localstack.cloud:4566"

BUCKET_NAME="s3-lambda-test"
KEY_NAME="testFile.json"

echo "Uploading file to S3 bucket..."
awslocal --endpoint-url=$LOCALSTACK_URL s3 cp "$KEY_NAME" s3://$BUCKET_NAME/


awslocal --endpoint-url=$LOCALSTACK_URL s3 ls s3://$BUCKET_NAME

# Check if the upload was successful
if [ $? -eq 0 ]; then
    echo "File uploaded successfully!"
else
    echo "Failed to upload file."
    exit 1
fi
