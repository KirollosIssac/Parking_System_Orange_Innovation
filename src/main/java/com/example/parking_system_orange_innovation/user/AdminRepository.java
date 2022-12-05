package com.example.parking_system_orange_innovation.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin getAdminByUserName(String userName);

    Optional<Admin> findAdminByUserName(String username);

}
