package com.example.parking_system_orange_innovation;

import com.example.parking_system_orange_innovation.slot.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private final SlotService slotService;

    public ScheduledTasks(SlotService slotService) {
        this.slotService = slotService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void timeIs12AM() {
        slotService.freeSlots();
    }

}
