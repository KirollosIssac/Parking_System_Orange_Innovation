package com.example.parking_system_orange_innovation.user;

import com.example.parking_system_orange_innovation.dto.CurrentAdminDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public CurrentAdminDTO getCurrentAdmin(String userName) {
        Admin admin = adminRepository.getAdminByUserName(userName);
        CurrentAdminDTO currentAdminDTO = CurrentAdminDTO.builder().userId(admin.getId()).build();
        return currentAdminDTO;
    }

    public Admin addAdmin(Admin admin) {
        adminRepository.save(admin);
        return admin;
    }



}
