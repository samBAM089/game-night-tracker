package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.*;
import de.sambam.gamenighttracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/games")
    public List<Game> getGameList() {
        return userService.listAllGames("1");
    }

    @GetMapping("/players")
    public List<PlayerDto> getPlayerList() {
        return userService.getAllPlayersList("1");
    }

    @PostMapping("/game")
    public Game addNewGame(@RequestBody Game gameToAdd) {
        return userService.addNewGame(gameToAdd, "1");
    }

    @PostMapping("/game/{apiGameId}/gamesessions")
    public GameSession addNewGameSession(@RequestBody GameSession newSession, @PathVariable("apiGameId") String apiGameId) {
        return userService.addNewGameSession(newSession, "1", apiGameId);
    }

}

