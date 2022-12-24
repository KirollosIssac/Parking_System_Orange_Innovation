package com.example.parking_system_orange_innovation.client_test;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.car.CarIsNotAssignedToClientException;
import com.example.parking_system_orange_innovation.car.CarNotFoundException;
import com.example.parking_system_orange_innovation.car.CarRepository;
import com.example.parking_system_orange_innovation.slot.CarIsNotActiveException;
import com.example.parking_system_orange_innovation.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @Mock
    CarRepository carRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    public void assignCarToClient() throws CarIsAlreadyAssignedToClientException, ClientIsNotActiveException,
            ClientNotFoundException, CarNotFoundException, CarIsNotActiveException {
        Car car = Car.builder()
                .plateNumber("111")
                .color("Black")
                .isAssigned(true)
                .isActive(false)
                .isParked(false)
                .build();
        Client client = Client.builder()
                .id(1L)
                .name("Kirollos")
                .role("CLIENT")
                .userName("KirollosIssac")
                .password("Kirollos")
                .email("KirollosIssac@gmail.com")
                .phoneNumber("01254638540")
                .isActive(false)
                .isVIP(false)
                .registrationDate(Instant.now())
                .build();
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        boolean thrown = false;
        try {
            clientService.assignCarToClient(1L,1L);
        } catch (ClientIsNotActiveException clientIsNotActiveException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        thrown = false;
        client.setIsActive(true);
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        try {
            clientService.assignCarToClient(1L, 1L);
        } catch (CarIsNotActiveException carIsNotActiveException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        thrown = false;
        car.setIsActive(true);
        try {
            clientService.assignCarToClient(1L, 1L);
        } catch (CarIsAlreadyAssignedToClientException carIsAlreadyAssignedToClientException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        car.setIsAssigned(false);
        Optional<Client> returnedClient = clientService.assignCarToClient(1L, 1L);
        assertThat(returnedClient.get().getCar()).isNotNull();
        assertThat(returnedClient.get().getCar().getIsAssigned()).isTrue();
    }

    @Test
    public void deassignCar() throws CarIsNotAssignedToClientException, ClientNotFoundException,
            CarNotFoundException, CarIsNotAssignedToThisClientException, ClientDoNotHaveCarException {
        Car car = Car.builder()
                .plateNumber("111")
                .color("Black")
                .isAssigned(false)
                .isActive(false)
                .isParked(false)
                .build();
        Client client = Client.builder()
                .name("Kirollos")
                .role("CLIENT")
                .userName("KirollosIssac")
                .password("Kirollos")
                .email("KirollosIssac@gmail.com")
                .phoneNumber("01254638540")
                .isActive(false)
                .isVIP(false)
                .registrationDate(Instant.now())
                .build();
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        boolean thrown = false;
        try {
            clientService.deassignCar(1L, 1L);
        } catch (ClientDoNotHaveCarException clientDoNotHaveCarException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        thrown = false;
        client.setCar(car);
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        try {
            clientService.deassignCar(1L, 1L);
        } catch (CarIsNotAssignedToClientException carIsNotAssignedToClientException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        thrown = false;
        car.setIsAssigned(true);
        client.setCar(car);
        Car NotassignedCar = Car.builder()
                .id(2L)
                .plateNumber("222")
                .color("Black")
                .isAssigned(true)
                .isActive(true)
                .isParked(false)
                .build();
        Mockito.when(carRepository.findById(2L)).thenReturn(Optional.of(NotassignedCar));
        try {
            clientService.deassignCar(1L, 2L);
        } catch (CarIsNotAssignedToThisClientException carIsNotAssignedToThisClientException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        Optional<Client> returnedClient = clientService.deassignCar(1L, 1L);
        assertThat(returnedClient.get().getCar()).isNull();
    }

    @Test
    public void addClient() throws PhoneNumberExistsException, UserNameExistsException, InvalidDataException,
            EmailExistsException {
        Client client1 = Client.builder()
                .name("Kirollos")
                .role("CLIENT")
                .userName("KirollosIssac")
                .password("Kirollos")
                .email("KirollosIssac@gmail.com")
                .phoneNumber("01254698530")
                .isActive(false)
                .isVIP(false)
                .registrationDate(Instant.now())
                .build();
        Client client2 = Client.builder()
                .name("Kirollos")
                .userName("KirollosIssac")
                .password("Kirollos")
                .email("KirollosIssac@gmail.com")
                .phoneNumber("01254698530")
                .isActive(false)
                .isVIP(false)
                .registrationDate(Instant.now())
                .build();
        Mockito.when(clientRepository.findClientByUserName(client2.getUserName())).thenReturn(Optional.of(client1));
        Client returnedClient;
        boolean thrown = false;
        try {
            returnedClient = clientService.addClient(client2);
        } catch (UserNameExistsException userNameExistsException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        thrown = false;
        client2.setUserName("Kirollos111");
        Mockito.when(clientRepository.findClientByEmail(client2.getEmail())).thenReturn(Optional.of(client1));
        try {
            returnedClient = clientService.addClient(client2);
        } catch (EmailExistsException emailExistsException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        thrown = false;
        client2.setEmail("Kirollos@gmail.com");
        Mockito.when(clientRepository.findClientByPhoneNumber(client2.getPhoneNumber())).thenReturn(Optional.of(client1));
        try {
            returnedClient = clientService.addClient(client2);
        } catch (PhoneNumberExistsException phoneNumberExistsException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        thrown = false;
        client2.setPhoneNumber("022535453");
        client2.setEmail("Kirollos@gmail");
        try {
            returnedClient = clientService.addClient(client2);
        } catch (InvalidDataException invalidDataException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        thrown = false;
        client2.setEmail("Kirollos@gmail.com");
        try {
            returnedClient = clientService.addClient(client2);
        } catch (InvalidDataException invalidDataException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        client2.setPhoneNumber("01565870024");
        returnedClient = clientService.addClient(client2);
        assertThat(returnedClient.getRole()).isEqualTo("CLIENT");
        assertThat(returnedClient.getIsActive()).isTrue();
    }

}
