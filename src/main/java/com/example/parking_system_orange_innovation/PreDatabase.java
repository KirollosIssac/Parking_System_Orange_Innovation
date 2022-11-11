package com.example.parking_system_orange_innovation;

import com.example.parking_system_orange_innovation.car.*;
import com.example.parking_system_orange_innovation.parking.*;
import com.example.parking_system_orange_innovation.slot.*;
import com.example.parking_system_orange_innovation.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.List;

@Configuration
public class PreDatabase {

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final SlotRepository slotRepository;

    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private final ParkingRepository parkingRepository;

    public PreDatabase(CarRepository carRepository, SlotRepository slotRepository, ClientRepository clientRepository, ParkingRepository parkingRepository) {
        this.carRepository = carRepository;
        this.slotRepository = slotRepository;
        this.clientRepository = clientRepository;
        this.parkingRepository = parkingRepository;
    }

    @Bean
    CommandLineRunner commandLineRunner() {

        return args -> {

            Client RECORD_1 = Client.builder().name("Kirollos").userName("KirollosIssac").email("KirollosIssac@gmail.com")
                    .password("Kirollos").phone_number("01150901394").IsVIP(false)
                    .registrationDate(Instant.now()).IsActive(true).build();
            Client RECORD_2 = Client.builder().name("George").userName("GeorgeIssac").email("GeorgeIssac@gmail.com")
                    .password("George").phone_number("01118067093").IsVIP(true)
                    .registrationDate(Instant.now()).IsActive(true).build();

            clientRepository.saveAll(List.of(RECORD_1, RECORD_2));

            Car RECORD_3 = Car.builder().client(RECORD_1).plateNumber("123").color("Black").IsParked(false)
                    .registrationDate(Instant.now()).IsActive(true).build();
            Car RECORD_4 = Car.builder().client(RECORD_2).plateNumber("456").color("White").IsParked(false)
                    .registrationDate(Instant.now()).IsActive(true).build();

            carRepository.saveAll(List.of(RECORD_3, RECORD_4));

            Slot RECORD_5 = Slot.builder().IsAVAILABLE(true).IsVIP(false).build();
            Slot RECORD_6 = Slot.builder().IsAVAILABLE(true).IsVIP(true).build();

            slotRepository.saveAll(List.of(RECORD_5, RECORD_6));

            Parking RECORD_7 = Parking.builder().slot(RECORD_5).car(RECORD_3).startParking(Instant.now()).build();
            Parking RECORD_8 = Parking.builder().slot(RECORD_6).car(RECORD_4).startParking(Instant.now()).build();

            parkingRepository.saveAll(List.of(RECORD_7, RECORD_8));

        };

    }
}
