package com.example.parking_system_orange_innovation.slot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    List<Slot> findSlotsByIsAvailableIsTrueAndIsVIPIsTrue();

    List<Slot> findSlotsByIsAvailableIsTrueAndIsVIPIsFalse();

}
