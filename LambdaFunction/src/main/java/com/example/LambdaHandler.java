package com.example;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.example.model.Price;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaHandler implements RequestHandler<S3Event, String> {

    Logger logger = LoggerFactory.getLogger(LambdaHandler.class);

    private static final String SQL_SERVER_HOST = System.getenv("SQL_SERVER_HOST");
    private static final String SQL_SERVER_USER = System.getenv("SQL_SERVER_USER");
    private static final String SQL_SERVER_PASS = System.getenv("SQL_SERVER_PASS");
    private static final String SQL_SERVER_URL = "jdbc:mysql://" + SQL_SERVER_HOST + ":3306/json_database?allowPublicKeyRetrieval=true&useSSL=false";

    @Override
    public String handleRequest(S3Event event, Context context) {
        BasicConfigurator.configure();
        logger.info("***** STARTING THE HANDLER FUNCTION *****");
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://s3.us-east-1.localhost.localstack.cloud:4566", "us-east-1")).build();

        String bucketName = event.getRecords().get(0).getS3().getBucket().getName();
        String objectKey = event.getRecords().get(0).getS3().getObject().getKey();

        logger.info("Bucket name: {}, object key: {}", bucketName, objectKey);

        S3ObjectWrapper s3ObjectWrapper = new S3ObjectWrapper(s3Client.getObject(bucketName, objectKey));
        try (Scanner scanner = new Scanner(s3ObjectWrapper.getObjectContent())) {
            String data = scanner.useDelimiter("\\A").next();

            logger.info("Size of the input data: {}", data.length());
            logger.info("Data: {}", data);

            ObjectMapper mapper = new ObjectMapper();

            List<Price> priceList = mapper.readValue(data, new TypeReference<List<Price>>(){});

            try (Connection conn = DriverManager.getConnection(SQL_SERVER_URL, SQL_SERVER_USER, SQL_SERVER_PASS)) {
                StringBuilder query = new StringBuilder("INSERT INTO json_database.s3_data (id, name, value) VALUES");

                Iterator<Price> iterator = priceList.iterator();

                while (iterator.hasNext()) {
                    Price price = iterator.next();
                    query.append("(").append(price.id).append(",'").append(price.name).append("','").append(price.value).append("')");
                    if(iterator.hasNext()) {
                        query.append(",");
                    }
                }

                query.append(";");

                logger.info("Query: {}", query);
                try (PreparedStatement pstmt = conn.prepareStatement(String.valueOf(query))) {
                    logger.info("***** SQL Statement: {} *****", pstmt);
                    pstmt.executeUpdate();
                    logger.info("SQL Insert Done");
                }
            }
        } catch (Exception e) {
            logger.error("Following exception occurred: {}", e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

        return "Data inserted successfully";
    }
}
