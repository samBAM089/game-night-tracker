package de.sambam.gamenighttracker.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidGenerator {
    public String generateUuiD() {
        return UUID.randomUUID().toString();
    }
}
