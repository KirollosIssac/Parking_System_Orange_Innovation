package com.example.parking_system_orange_innovation.user;

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

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getCarOwner(Long carId) {
        return clientRepository.findClientByCar_Id(carId);
    }

    public boolean existsByUserName(String userName) {
        return clientRepository.existsByUserName(userName);
    }

    @Transactional
    /*public Car assignCarToClient(Long clientID, Car car) {
        Optional<Client> optionalClient = clientRepository.findById(clientID);
        optionalClient.get().getCar().add(car);
        return car;
    }*/

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
        if (!optionalClient.isPresent()){
            clientRepository.save(client);
        }else{
        optionalClient.get().setCar(client.getCar());
        optionalClient.get().setName(client.getName());
        optionalClient.get().setIsActive(client.getIsActive());
//        optionalClient.get().isVip(client.isVip());
        optionalClient.get().setPhoneNumber(client.getPhoneNumber());
        optionalClient.get().setPassword((client.getPassword()));
        }
        return optionalClient;
    }

    @Transactional
    public void deleteClientCar(Long carId) {
        Optional<Client> client = getCarOwner(carId);
        if (client.isPresent())
            client.get().setCar(null);
    }

}
