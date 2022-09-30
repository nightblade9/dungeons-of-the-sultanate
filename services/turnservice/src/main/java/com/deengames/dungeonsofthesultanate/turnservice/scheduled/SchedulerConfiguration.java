package com.deengames.dungeonsofthesultanate.turnservice.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {

    // Zone doesn't matter now; it could if we start doing things daily instead of hourly.
    @Scheduled(cron = "${dots.tickCron}", zone = "GMT")
    public void incrementTurnsEveryHour() {
        // TODO: if this takes too long, consider spawning threads here, and partitioning data.
        // As per Spring scheduler requirements, no params can go in here, and all scheduled
        // threads share a single executor, so parallalize *within* this method.

        // ALSO: don't assume that exactly an hour passed; check how long elapsed since last tick.
        System.out.println(new java.util.Date() + " => Hello from CRON");
    }
}
