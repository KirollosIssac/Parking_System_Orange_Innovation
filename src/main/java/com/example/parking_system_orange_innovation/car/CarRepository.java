package com.example.parking_system_orange_innovation.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findCarByPlateNumber(String plateNumber);

    void deleteCarById(Long id);

}
