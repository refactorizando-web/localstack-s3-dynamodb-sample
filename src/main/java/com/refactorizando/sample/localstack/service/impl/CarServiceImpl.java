package com.refactorizando.sample.localstack.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.refactorizando.sample.localstack.model.Car;
import com.refactorizando.sample.localstack.repository.CarRepository;
import com.refactorizando.sample.localstack.service.CarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final AmazonS3 amazonS3;

    private final CarRepository carRepository;


    @Override
    public Car saveCar(Car car) {

        return carRepository.save(car);

    }

    @Override
    public List<Car> findAll() {
        return StreamSupport
                .stream(carRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> findByColor(String color) {

        return carRepository.findByColor(color);

    }

    @Override
    public List<Car> findByModel(String model) {
        return carRepository.findByModel(model);
    }

    @Override
    public List<String> getAllDocumentsFromBuckets(String bucketName) {

        if (!amazonS3.doesBucketExistV2(bucketName)) {
            log.info("Bucket name is not available."
                    + " Try again with a different Bucket name.");
            throw new NoSuchElementException("Bucket name is not available");
        }

        return amazonS3.listObjectsV2(bucketName).getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public void createBucket(String bucketName) {

        amazonS3.createBucket(bucketName);
    }

    @Override
    public void uploadDocument(MultipartFile file, String bucketName) throws IOException {

        String tempFileName = UUID.randomUUID() + file.getName();
        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + tempFileName);
        file.transferTo(tempFile);
        amazonS3.putObject(bucketName, UUID.randomUUID() + file.getName(), tempFile);
        tempFile.deleteOnExit();
    }
}
