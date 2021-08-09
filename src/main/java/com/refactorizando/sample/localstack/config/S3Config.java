package com.refactorizando.sample.localstack.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${aws.s3.endpoint}")
    private String awsEndpoint;

    @Value("${aws.accesskey}")
    private String awsAccessKey;

    @Value("${aws.secretkey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;


    @Bean
    public AmazonS3 s3() {

        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsEndpoint, awsRegion))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .build();

    }
}
