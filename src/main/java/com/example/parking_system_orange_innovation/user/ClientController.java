package com.example.parking_system_orange_innovation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "clients")
public class ClientController {

    @Autowired
    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/getClients")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping("/addClient")
    public Client addClient(@RequestBody Client client) throws UserNameExistsException, EmailExistsException, WeakPasswordException {
        return clientService.addClient(client);
    }

    @PutMapping("/updateClient")
    public Optional<Client> updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }

}
