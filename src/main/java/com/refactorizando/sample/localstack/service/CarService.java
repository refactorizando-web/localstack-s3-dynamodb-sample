package com.refactorizando.sample.localstack.service;

import com.refactorizando.sample.localstack.model.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarService {

    Car saveCar(Car car);

    List<Car> findAll();

    List<Car> findByColor(String color);

    List<Car> findByModel(String color);

    List<String> getAllDocumentsFromBuckets(String bucketName);

    void createBucket(String bucketName);

    void uploadDocument(MultipartFile file, String bucketName) throws IOException;



}
