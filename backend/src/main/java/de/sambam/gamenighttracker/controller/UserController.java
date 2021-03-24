package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.GameSession;
import de.sambam.gamenighttracker.model.Player;
import de.sambam.gamenighttracker.model.User;
import de.sambam.gamenighttracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/gamesessions")
    public List<GameSession> getGameSessionsList() {
        return userService.listAllSessions("1");
    }

    @GetMapping("/players")
    public List<Player> getPlayerList() {
        return userService.listAllPlayers("1");
    }
}

