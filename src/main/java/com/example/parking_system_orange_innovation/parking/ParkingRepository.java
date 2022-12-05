package com.example.parking_system_orange_innovation.parking;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

    //List<Parking> findParkingsBySlot_Car_Id(Long carID);
    List<Parking> findParkingsByCarId(Long carID);

    Optional<Parking> findParkingByCarIdAndIsFinishedIsFalse(Long carID);
}
