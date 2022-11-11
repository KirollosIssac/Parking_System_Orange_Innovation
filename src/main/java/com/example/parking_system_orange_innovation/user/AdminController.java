package com.example.parking_system_orange_innovation.user;

import com.example.parking_system_orange_innovation.user.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "admins")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(path = "/getAdmins")
    public List<Admin> getAdmins() {
        return adminService.getAllAdmins();
    }

    @PostMapping(path = "/addAdmin")
    public Admin addAdmin(@RequestBody Admin admin) {
        adminService.addAdmin(admin);
        return admin;
    }

}
