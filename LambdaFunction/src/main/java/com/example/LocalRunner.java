//package com.example;
//
//
////iimport com.amazonaws.services.lambda.runtime.events.S3Event;
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.events.S3Event;
//import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
//
//import java.sql.Date;
//import java.util.Arrays;
//
//public class LocalRunner {
//    public static void main(String[] args) {
//        // Initialize your Lambda handler
//        LambdaHandler handler = new LambdaHandler();
//
//        // Create a mock S3Event (you need to provide a valid mock event)
//        S3Event s3Event = createMockS3Event();
//
//
//        // Invoke the Lambda handler
//        String result = handler.handleRequest(s3Event, null);
//
//        // Print the result
//        System.out.println(result);
//    }
//
//    private static S3Event createMockS3Event() {
//        // Create a mock S3Event or load from a JSON file
//        // Example: Use real or mock JSON for S3Event
//        String json = "testiingngngn";
//
//        // Manually construct S3EventNotificationRecord
//        S3EventNotification.S3BucketEntity bucket = new S3EventNotification.S3BucketEntity("my-test-bucket", new S3EventNotification.UserIdentityEntity("user-id"), "arn:aws:s3:::my-bucket");
//        S3EventNotification.S3ObjectEntity object = new S3EventNotification.S3ObjectEntity("file.json", 12345L, "eTag", null, "versionId");
//
//        S3EventNotification.S3Entity s3Entity = new S3EventNotification.S3Entity("configurationId", bucket, object, "request-id");
//
////        S3Entity s3Entity = new S3Entity("configurationId", bucket, object, "request-id");
//        S3EventNotification.S3EventNotificationRecord record = new S3EventNotification.S3EventNotificationRecord(
//                "us-east-1",
//                "ObjectCreated:Put",
//                "aws:s3",
//                Date.valueOf("2024-08-27").toString(),
//                "2.1",
//                new S3EventNotification.RequestParametersEntity("127.0.0.1"),
//                new S3EventNotification.ResponseElementsEntity("response-element-1", "response-element-2"),
//                s3Entity,
//                new S3EventNotification.UserIdentityEntity("principal-id")
//        );
//
//        return new S3Event(Arrays.asList(record));
//    }
//}
