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

    @GetMapping("/getAllClients")
    public ResponseEntity<List<Client>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/getClientById")
    public ResponseEntity<ClientDTO> getClientById(@RequestBody String userName) {
        try {
            Optional<Client> client = clientService.getClientById(userName);
            ClientDTO clientDTO = ClientDTO.builder().id(client.get().getId())
                    .userName(client.get().getUserName())
                    .role(client.get().getRole())
                    .isVIP(client.get().getIsVIP())
                    .isActive(client.get().getIsActive())
                    .build();
            return new ResponseEntity<>(clientDTO, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<CurrentClientDTO> getCurrentClient() {
        try {
            return new ResponseEntity<>(clientService.getCurrentClient(SecurityContextHolder.getContext()
                    .getAuthentication().getName()), HttpStatus.OK);
        } catch(ClientNotFoundException clientNotFoundException) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @PutMapping("/updateClient")
    public ResponseEntity<String> updateClient(@RequestBody Client client) {
        try {
            clientService.updateClient(client);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
    }

    @PutMapping("/assignCar")
    public ResponseEntity<String> assignCar(@RequestBody ClientCarAssignmentDTO clientCarAssignmentDTO) {
        try {
            clientService.assignCarToClient(clientCarAssignmentDTO.getClientId(), clientCarAssignmentDTO.getCarId());
            return new ResponseEntity<>("Car assigned successfully!", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/deassignCar")
    public ResponseEntity<String> deassignCar(@RequestBody ClientCarAssignmentDTO clientCarAssignmentDTO) {
        try {
            clientService.deassignCar(clientCarAssignmentDTO.getClientId(), clientCarAssignmentDTO.getCarId());
            return new ResponseEntity<>("Car deassigned successfully!", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deleteClient/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable("clientId") Long clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }

}
