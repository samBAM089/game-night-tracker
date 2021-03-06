package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.model.*;
import de.sambam.gamenighttracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sessions")
    public List<GameSessionDto> getAllGamesSessions(Principal principal) {
        String loggedInUsername = principal.getName();
        return userService.listAllSessions(loggedInUsername);

    }

    @GetMapping("/games")
    public List<Game> getGameList(Principal principal) {
        String loggedInUsername = principal.getName();
        return userService.listAllGames(loggedInUsername);
    }

    @GetMapping("/players")
    public List<PlayerDto> getPlayerList(Principal principal) {
        String loggedInUsername = principal.getName();
        return userService.listAllPlayers(loggedInUsername);
    }

    @PostMapping("/game")
    public Optional<Game> addNewGame(@RequestBody Game gameToAdd, Principal principal) {
        String loggedInUsername = principal.getName();
        try {
            return userService.addNewGame(gameToAdd, loggedInUsername);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No gamelist was generated!");
        }
    }

    @PostMapping("/savesession")
    public Optional<GameSession> addNewGameSession(@RequestBody GameSessionDto newSessionDto,
                                                   Principal principal) {
        String loggedInUsername = principal.getName();
        try {
            return userService.addNewGameSession(newSessionDto, loggedInUsername, newSessionDto.getApiGameId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No sessionList was generated!");
        }
    }


}


