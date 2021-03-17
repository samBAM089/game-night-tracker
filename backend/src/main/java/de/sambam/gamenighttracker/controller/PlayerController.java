package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.model.Player;
import de.sambam.gamenighttracker.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public Player addNewPlayer(@RequestBody Player newPlayer) {
        playerService.addPlayer(newPlayer);
        return newPlayer;
    }
}
