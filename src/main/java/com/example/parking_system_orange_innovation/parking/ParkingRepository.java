package com.example.parking_system_orange_innovation.parking;

import com.example.parking_system_orange_innovation.parking.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

    Optional<Parking> findParkingsByCar_Client_Id(Long clientID);

    Optional<Parking> findParkingsByCar_Id(Long carID);

}
