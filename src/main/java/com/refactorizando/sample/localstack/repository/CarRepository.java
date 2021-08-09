package com.refactorizando.sample.localstack.repository;

import com.refactorizando.sample.localstack.model.Car;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface CarRepository extends CrudRepository<Car, String> {

    List<Car> findByModel(String model);

    List<Car> findByColor (String color);
}
