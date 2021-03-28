package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.model.*;
import de.sambam.gamenighttracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        return userService.listAllPlayers("1");
    }

    @PostMapping("/game")
    public Optional<Game> addNewGame(@RequestBody Game gameToAdd) {
        try {
            return userService.addNewGame(gameToAdd, "1");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No gamelist was generated!");
        }
    }

    @PostMapping("/game/{apiGameId}/gamesessions")
    public Optional<GameSession> addNewGameSession(@RequestBody GameSession newSession, @PathVariable String apiGameId) {
        try {
            return userService.addNewGameSession(newSession, "1", apiGameId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No sessionList was generated!");
        }
    }
}


