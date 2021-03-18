package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Game addNewGameToDb(@RequestBody Game newGame) {
        return gameService.addNewGame(newGame);
    }

}
