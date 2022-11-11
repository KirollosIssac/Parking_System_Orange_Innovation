package com.example.parking_system_orange_innovation.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByUserName(String userName);

    Optional<Client> findClientByEmail(String email);

    Optional<Client> findClientByPassword(String password);

}
