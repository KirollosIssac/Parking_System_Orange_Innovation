package com.example.parking_system_orange_innovation.slot_test;

import com.example.parking_system_orange_innovation.car.Car;
import com.example.parking_system_orange_innovation.car.CarRepository;
import com.example.parking_system_orange_innovation.slot.*;
import com.example.parking_system_orange_innovation.user.CarIsNotAssignedToClient;
import com.example.parking_system_orange_innovation.user.Client;
import com.example.parking_system_orange_innovation.user.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class SlotServiceTest {

    @Mock
    SlotRepository slotRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    ClientService clientService;

    @InjectMocks
    SlotService slotService;

    @Test
    public void freeSlots() {
        Slot slot = Slot.builder()
                .isActive(true)
                .isAvailable(false)
                .build();
        Mockito.when(slotRepository.findAll()).thenReturn(List.of(slot));
        Optional<List<Slot>> slots = slotService.freeSlots();
        assertThat(slots.get().get(0).getIsAvailable()).isTrue();
    }

    @Test
    public void freeSlot() throws SlotIsNotActiveException {
        Car car = Car.builder()
                .plateNumber("111")
                .color("Black")
                .isAssigned(true)
                .isActive(true)
                .isParked(true)
                .build();
        Slot slot = Slot.builder()
                .car(car)
                .isAvailable(false)
                .isActive(false)
                .isVIP(false)
                .build();
        Mockito.when(slotRepository.findById(1L)).thenReturn(Optional.of(slot));
        boolean thrown = false;
        try {
            slotService.freeSlot(1L);
        } catch (SlotIsNotActiveException slotIsNotActiveException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        slot.setIsActive(true);
        Optional<Slot> returnedSlot = slotService.freeSlot(1L);
        assertThat(returnedSlot.get().getIsAvailable()).isTrue();
        assertThat(returnedSlot.get().getCar()).isNull();
    }

    @Test
    public void assignSlot() throws VIPSlotException, CarNotFoundException, SlotIsNotActiveException,
            CarIsNotAssignedToClient, CarIsNotActiveException {
        Car car = Car.builder()
                .plateNumber("111")
                .color("Black")
                .isAssigned(false)
                .isActive(false)
                .isParked(false)
                .build();
        Slot slot = Slot.builder()
                .isAvailable(true)
                .isActive(false)
                .isVIP(true)
                .build();
        Client client = Client.builder()
                .isVIP(false)
                .build();
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        Mockito.when(slotRepository.findById(1L)).thenReturn(Optional.of(slot));
        Mockito.when(clientService.getCarOwner(1L)).thenReturn(Optional.of(client));
        boolean thrown = false;
        try {
            slotService.assignSlot(1L, 1L);
        } catch (CarIsNotActiveException carIsNotActiveException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        thrown = false;
        car.setIsActive(true);
        try {
            slotService.assignSlot(1L, 1L);
        } catch (SlotIsNotActiveException slotIsNotActiveException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        slot.setIsActive(true);
        thrown = false;
        try {
            slotService.assignSlot(1L, 1L);
        } catch (VIPSlotException vipSlotException) {
            thrown = true;
        }
        assertThat(thrown).isTrue();
        slot.setIsVIP(false);
        Optional<Slot> returnedSlot = slotService.assignSlot(1L, 1L);
        assertThat(returnedSlot.get().getCar()).isNotNull();
        assertThat(returnedSlot.get().getIsAvailable()).isFalse();
        assertThat(returnedSlot.get().getCar().getIsParked()).isTrue();
    }
}
