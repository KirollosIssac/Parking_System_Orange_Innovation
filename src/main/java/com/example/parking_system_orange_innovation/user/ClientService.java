package com.example.parking_system_orange_innovation.user;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.car.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private final CarRepository carRepository;

    public ClientService(ClientRepository clientRepository, CarRepository carRepository) {
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public Car assignCarToClient(Long clientID, Car car) {
        Optional<Client> optionalClient = clientRepository.findById(clientID);
        optionalClient.get().getCars().add(car);
        return car;
    }

    public Client addClient(Client client) throws UserNameExistsException, EmailExistsException, WeakPasswordException {
        Optional<Client> optionalClient = clientRepository.findClientByUserName(client.getUserName());
        if (optionalClient.isPresent())
            throw new UserNameExistsException();
        optionalClient = clientRepository.findClientByEmail(client.getEmail());
        if (optionalClient.isPresent())
            throw new EmailExistsException();
        optionalClient = clientRepository.findClientByPassword(client.getPassword());
        if (optionalClient.isPresent())
            throw new WeakPasswordException();
        clientRepository.save(client);
        return client;
    }

    @Transactional
    public Optional<Client> updateClient(Client client) {
        Optional<Client> optionalClient = clientRepository.findById(client.getId());
        optionalClient.get().setCars(client.getCars());
        optionalClient.get().setName(client.getName());
        optionalClient.get().setIsActive(client.isIsActive());
        optionalClient.get().setIsVIP(client.isIsVIP());
        optionalClient.get().setPhone_number(client.getPhone_number());
        optionalClient.get().setPassword((client.getPassword()));
        return optionalClient;
    }

}
