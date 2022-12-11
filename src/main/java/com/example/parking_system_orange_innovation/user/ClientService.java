package com.example.parking_system_orange_innovation.user;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.car.CarIsNotAssignedToClientException;
import com.example.parking_system_orange_innovation.car.CarNotFoundException;
import com.example.parking_system_orange_innovation.car.CarRepository;
import com.example.parking_system_orange_innovation.dto.CurrentClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, CarRepository carRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClient(String userName) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findClientByUserName(userName);
        if (!optionalClient.isPresent())
            throw new ClientNotFoundException();
        return optionalClient;
    }

    public CurrentClientDTO getCurrentClient(String userName) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findClientByUserName(userName);
        if (!optionalClient.isPresent())
            throw new ClientNotFoundException();
        CurrentClientDTO currentClientDTO = CurrentClientDTO.builder().userId(optionalClient.get().getId())
                .userName(userName).isVIP(optionalClient.get().getIsVIP()).isActive(optionalClient.get().getIsActive())
                .carId(optionalClient.get().getCar().getId()).isParked(optionalClient.get().getCar().getIsParked())
                .build();
        return currentClientDTO;
    }

    public Optional<Client> getCarOwner(Long carId) {
        return clientRepository.findClientByCar_Id(carId);
    }

    public boolean existsByUserName(String userName) {
        return clientRepository.existsByUserName(userName);
    }

    @Transactional
    public void assignCarToClient(Long clientID, Long carID) throws CarIsAlreadyAssignedToClientException {
        Optional<Client> optionalClient = clientRepository.findById(clientID);
        Optional<Car> optionalCar = carRepository.findById(carID);
        if (!optionalCar.get().getIsAssigned()) {
            optionalClient.get().setCar(optionalCar.get());
            optionalCar.get().setIsAssigned(true);
        }
        else
            throw new CarIsAlreadyAssignedToClientException();
    }

    @Transactional
    public void deassignCar(Long clientId, Long carId) throws ClientNotFoundException, CarNotFoundException,
            ClientDoNotHaveCarException, CarIsNotAssignedToClientException, CarIsNotAssignedToThisClientException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (!optionalClient.isPresent())
            throw new ClientNotFoundException();
        if (optionalClient.get().getCar() == null)
            throw new ClientDoNotHaveCarException();
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (!optionalCar.isPresent())
            throw new CarNotFoundException();
        if (!optionalCar.get().getIsAssigned())
            throw new CarIsNotAssignedToClientException();
        if (optionalClient.get().getCar().getId() != optionalCar.get().getId())
            throw new CarIsNotAssignedToThisClientException();
        optionalClient.get().setCar(null);
        optionalCar.get().setIsAssigned(false);
    }

    public Client addClient(Client client) throws UserNameExistsException, EmailExistsException, PhoneNumberExistsException, InvalidDataException {
        Optional<Client> optionalClient = clientRepository.findClientByUserName(client.getUserName());
        if (optionalClient.isPresent())
            throw new UserNameExistsException();
        optionalClient = clientRepository.findClientByEmail(client.getEmail());
        if (optionalClient.isPresent())
            throw new EmailExistsException();
        optionalClient = clientRepository.findClientByPhoneNumber(client.getPhoneNumber());
        if (optionalClient.isPresent())
            throw new PhoneNumberExistsException();
        if (!client.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            throw new InvalidDataException();
        if (!client.getPhoneNumber().matches("^01[0125][0-9]{8}$"))
            throw new InvalidDataException();
        client.setRole("CLIENT");
        clientRepository.save(client);
        return client;
    }

    @Transactional
    public Optional<Client> updateClient(Client client) throws PhoneNumberExistsException {
        Optional<Client> optionalClient;
        optionalClient = clientRepository.findClientByUserName(client.getPhoneNumber());
        if (optionalClient.isPresent())
            throw new PhoneNumberExistsException();
        optionalClient = clientRepository.findById(client.getId());
        if (optionalClient.isPresent()) {
            optionalClient.get().setName(client.getName());
            optionalClient.get().setPhoneNumber(client.getPhoneNumber());
            optionalClient.get().setIsVIP(client.getIsVIP());
            optionalClient.get().setIsActive(client.getIsActive());
        }
        return optionalClient;
    }

    @Transactional
    public void deleteClientCar(Long carId) {
        Optional<Client> client = getCarOwner(carId);
        if (client.isPresent())
            client.get().setCar(null);
    }

    @Transactional
    public void deleteClient(Long id) {
        clientRepository.deleteClientById(id);
    }

}
