package com.example.parking_system_orange_innovation.user;

import com.example.parking_system_orange_innovation.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "clients")
public class ClientController {

    @Autowired
    private final ClientService clientService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    public ClientController(ClientService clientService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/getClients")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @CrossOrigin()
    @PostMapping("/addClient")
    public ResponseEntity<String> addClient(@RequestBody ClientDTO clientDTO) throws UserNameExistsException, EmailExistsException, WeakPasswordException {
        System.out.print(clientDTO);
        if (clientService.existsByUserName(clientDTO.getUserName())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        Client client = Client.builder().name(clientDTO.getName())
                .userName(clientDTO.getUserName())
                .password(passwordEncoder.encode(clientDTO.getPassword()))
                .email(clientDTO.getEmail())
                .phoneNumber(clientDTO.getPhoneNumber())
                .isVIP(clientDTO.getIsVIP())
                .registrationDate(Instant.now())
                .isActive(clientDTO.getIsActive())
                .build();
        clientService.addClient(client);
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @PutMapping("/updateClient")
    public Optional<Client> updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }

}
