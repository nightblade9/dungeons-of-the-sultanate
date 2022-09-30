package com.deengames.dungeonsofthesultanate.turnservice.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Scheduled(cron = "${dots.tickCron}", zone = "GMT")
    public void incrementTurnsEveryHour() {
        // TODO: if this takes too long, consider spawning threads here, and partitioning data.
        // As per Spring scheduler requirements, no params can go in here, and all scheduled
        // threads share a single executor, so parallalize *within* this method.
        System.out.println(new java.util.Date() + " => Hello from CRON");
    }
}
