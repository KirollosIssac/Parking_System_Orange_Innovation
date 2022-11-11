package com.example.parking_system_orange_innovation.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "select * from Car where clientID = :clientID", nativeQuery = true)
    Optional<Car> getCars(@Param("clientID") Long clientID);

    Optional<Car> findCarByPlateNumber(String plateNumber);

}
