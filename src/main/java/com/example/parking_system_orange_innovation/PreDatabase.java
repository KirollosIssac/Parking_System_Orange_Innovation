package com.example.parking_system_orange_innovation;

import com.example.parking_system_orange_innovation.car.*;
import com.example.parking_system_orange_innovation.slot.*;
import com.example.parking_system_orange_innovation.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private final AdminRepository adminRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public PreDatabase(CarRepository carRepository, SlotRepository slotRepository, ClientRepository clientRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.carRepository = carRepository;
        this.slotRepository = slotRepository;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //@Bean
    CommandLineRunner commandLineRunner() {

        return args -> {

            Car RECORD_3 = Car.builder().plateNumber("123").color("Black").isParked(false)
                    .registrationDate(Instant.now()).isActive(true).isAssigned(true).build();
            Car RECORD_4 = Car.builder().plateNumber("456").color("White").isParked(false)
                    .registrationDate(Instant.now()).isActive(true).isAssigned(true).build();

            carRepository.saveAll(List.of(RECORD_3, RECORD_4));

            Client RECORD_1 = Client.builder().name("Kirollos").userName("KirollosIssac").email("KirollosIssac@gmail.com")
                    .password(passwordEncoder.encode("Kirollos")).phoneNumber("01150901394").isVIP(false)
                    .registrationDate(Instant.now()).isActive(true).role("CLIENT").car(RECORD_3).build();
            Client RECORD_2 = Client.builder().name("George").userName("GeorgeIssac").email("GeorgeIssac@gmail.com")
                    .password(passwordEncoder.encode("George")).phoneNumber("01118067093").isVIP(true)
                    .registrationDate(Instant.now()).isActive(true).role("CLIENT").car(RECORD_4).build();

            clientRepository.saveAll(List.of(RECORD_1, RECORD_2));


            Slot RECORD_5 = Slot.builder().isAvailable(true).isVIP(false).isActive(true).build();
            Slot RECORD_6 = Slot.builder().isAvailable(true).isVIP(true).isActive(true).build();

            slotRepository.saveAll(List.of(RECORD_5, RECORD_6));

            Admin RECORD_7 = Admin.builder().name("Steven").userName("steven12").email("stevenmitchell@gmail.com")
                    .password(passwordEncoder.encode("Steven")).role("ADMIN").build();

            adminRepository.saveAll(List.of(RECORD_7));

        };

    }
}
