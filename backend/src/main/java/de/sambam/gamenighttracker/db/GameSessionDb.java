package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.GameSession;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GameSessionDb {

    private final List<GameSession> gameSessionList;

    public GameSessionDb() {
        this.gameSessionList = new ArrayList<>();
    }

    public void addGameSession(GameSession gameSessionToAdd) {
        gameSessionList.add(gameSessionToAdd);
    }

    public List<GameSession> getGameSessionList() {
        return gameSessionList;
    }

    public void clear() {
        this.gameSessionList.clear();
    }
}
