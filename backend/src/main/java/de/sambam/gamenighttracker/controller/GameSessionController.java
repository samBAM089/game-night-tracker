package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.db.GameSessionDb;
import de.sambam.gamenighttracker.model.GameSession;
import de.sambam.gamenighttracker.service.GameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gamesessions")
public class GameSessionController {

    private GameSessionService gameSessionService;
    private GameSessionDb gameSessionDb;

    @Autowired
    public GameSessionController(GameSessionService gameSessionService, GameSessionDb gameSessionDb) {
        this.gameSessionService = gameSessionService;
        this.gameSessionDb = gameSessionDb;
    }

    @PostMapping
    public GameSession addNewGameSession(@RequestBody GameSession gameSessionToAdd) {
        gameSessionService.addNewGameSession(gameSessionToAdd);
        return gameSessionToAdd;
    }
}
