package com.refactorizando.sample.localstack.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.s3.AmazonS3;
import com.refactorizando.sample.localstack.BaseIntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest extends BaseIntegrationTest {

    @Autowired
    private CarService carService;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;


    @Autowired
    private AmazonS3 amazonS3;


    @Test
    public void verifyTableName() {

        assertThat(amazonDynamoDB.listTables().getTableNames().contains("car"));

    }

    @Test
    public void verifyS3Bucket() {
        Throwable throwable = catchThrowable(() -> carService.getAllDocumentsFromBuckets("aaaaa"));

        NoSuchElementException notFoundException = (NoSuchElementException) throwable;

        assertThat(notFoundException.getMessage()).isEqualTo("Bucket name is not available");

    }

    @Test
    public void createBucket() {

        carService.createBucket("aaaa");

        assertThat(amazonS3.listBuckets().contains("aaaa"));

    }
}