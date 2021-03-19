package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.GameSessionDb;
import de.sambam.gamenighttracker.model.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameSessionService {

    private final GameSessionDb gameSessionDb;

    @Autowired
    public GameSessionService(GameSessionDb gameSessionDb) {
        this.gameSessionDb = gameSessionDb;
    }


    public void addNewGameSession(GameSession gameSessionToAdd) {
        gameSessionDb.addGameSession(gameSessionToAdd);
    }
}
