package com.example.parking_system_orange_innovation.user;

import com.example.parking_system_orange_innovation.dto.AuthResponseDTO;
import com.example.parking_system_orange_innovation.dto.LoginDTO;
import com.example.parking_system_orange_innovation.dto.RoleDTO;
import com.example.parking_system_orange_innovation.security.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "auth")
public class AuthController {

    @Autowired
    private final ClientService clientService;

    @Autowired
    private final AdminService adminService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final Jwt jwt;

    public AuthController(ClientService clientService, AdminService adminService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, Jwt jwt) {
        this.clientService = clientService;
        this.adminService = adminService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }


    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwt.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @GetMapping(path = "/getRole")
    public  ResponseEntity<RoleDTO> getRole() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        RoleDTO roleDTO = new RoleDTO(role);
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }
}
