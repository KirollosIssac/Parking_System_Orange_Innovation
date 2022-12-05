package com.example.parking_system_orange_innovation.user;

import com.example.parking_system_orange_innovation.dto.ClientCarAssignmentDTO;
import com.example.parking_system_orange_innovation.dto.ClientDTO;
import com.example.parking_system_orange_innovation.dto.CurrentClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<List<Client>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/getClient")
    public ResponseEntity<ClientDTO> getClient(@RequestBody String userName) {
        Optional<Client> client = clientService.getClient(userName);
        ClientDTO clientDTO = ClientDTO.builder().id(client.get().getId())
                .userName(client.get().getUserName()).role(client.get().getRole())
                .isVIP(client.get().getIsVIP()).isActive(client.get().getIsActive()).build();
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<CurrentClientDTO> getCurrentClient() {
        return new ResponseEntity<>(clientService.getCurrentClient(SecurityContextHolder.getContext()
                .getAuthentication().getName()), HttpStatus.OK);
    }

    @PostMapping("/addClient")
    public ResponseEntity<String> addClient(@RequestBody ClientDTO clientDTO) {
        Client client = Client.builder().name(clientDTO.getName())
                .userName(clientDTO.getUserName())
                .password(passwordEncoder.encode(clientDTO.getPassword()))
                .email(clientDTO.getEmail())
                .phoneNumber(clientDTO.getPhoneNumber())
                .isVIP(false)
                .registrationDate(Instant.now())
                .isActive(true)
                .build();
        try {
            clientService.addClient(client);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @PutMapping("/updateClient")
    public ResponseEntity<String> updateClient(@RequestBody Client client) {
        try {
            clientService.updateClient(client);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
    }

    @PutMapping("/assignCar")
    public ResponseEntity<String> assignCar(@RequestBody ClientCarAssignmentDTO clientCarAssignmentDTO) {
        clientService.assignCarToClient(clientCarAssignmentDTO.getClientId(), clientCarAssignmentDTO.getCarId());
        return new ResponseEntity<>("Car assigned successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteClient/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable("clientId") Long clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }

}
