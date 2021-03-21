package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.GameDb;
import de.sambam.gamenighttracker.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Game> listAllGames() {
        return gameDb.getGameList();
    }

    public void deleteGame(String gameToDelete) {
        gameDb.delete(gameToDelete);
    }
}
