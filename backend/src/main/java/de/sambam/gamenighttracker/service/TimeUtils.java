package de.sambam.gamenighttracker.service;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TimeUtils {

    public Instant now() {
        return Instant.now();
    }

    ;
}
