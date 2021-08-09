package com.refactorizando.sample.localstack;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.localstack.LocalStackContainer;


@ContextConfiguration(initializers = BaseIntegrationTest.Initializer.class)
public abstract class BaseIntegrationTest {

    public static LocalStackContainer localStackContainer = new LocalStackContainer().withServices
            (LocalStackContainer.Service.S3, LocalStackContainer.Service.DYNAMODB);

    static {

        localStackContainer.start();

    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {


        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues values = TestPropertyValues.of(
                    "aws.dynamodb.endpoint=" +
                            localStackContainer.getEndpointConfiguration(LocalStackContainer.Service.DYNAMODB)
                                    .getServiceEndpoint(),
                    "aws.s3.endpoint=" +
                            localStackContainer.getEndpointConfiguration(LocalStackContainer.Service.S3)
                                    .getServiceEndpoint());


            values.applyTo(configurableApplicationContext);
        }
    }
}
