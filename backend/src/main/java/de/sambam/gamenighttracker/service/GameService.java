package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.GameDb;
import de.sambam.gamenighttracker.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameDb gameDb;

    @Autowired
    public GameService(GameDb gameDb) {
        this.gameDb = gameDb;
    }

    public Game addNewGame(Game newGame) {
        return gameDb.add(newGame);
    }
}
