package com.refactorizando.sample.localstack.controller;

import com.refactorizando.sample.localstack.model.Car;
import com.refactorizando.sample.localstack.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<Car> newCar(@Valid @RequestBody Car car) {
        return new ResponseEntity(carService.saveCar(car), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Car>> findCars(@Param("color") String color, @Param("model") String model) {

        if (null != color) {
            return ResponseEntity.ok(carService.findByColor(color));
        } else if (null != model){
            return ResponseEntity.ok(carService.findByModel(model));
        }
        return ResponseEntity.ok(carService.findAll());
    }


    @GetMapping("documents")
    public List<String> getAllDocuments(@Param("bucketName") String bucketName) {
        return carService.getAllDocumentsFromBuckets(bucketName);
    }

    @PutMapping("documents")
    public ResponseEntity saveDocument(@RequestParam(value = "file") MultipartFile file, @Param("bucketName") String bucketName) throws IOException {

        carService.uploadDocument(file, bucketName);

        return ResponseEntity.created(URI.create("SOME-LOCATION")).build();

    }

    @PostMapping("create-bucket")
    public ResponseEntity<String> create(@Param("bucketName") String bucketName) {
         carService.createBucket(bucketName);

        return ResponseEntity.ok(bucketName);

    }
}
