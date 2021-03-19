package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.GameSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameSessionDbTest {

    @Test
    @DisplayName("addGameSession method should add new game session to db")
    public void addGameSessionTest() {
        //GIVEN
        GameSessionDb gameSessionDb = new GameSessionDb();
        GameSession gameSession = GameSession.builder().gameName("CloudAge").build();

        //WHEN
        gameSessionDb.addGameSession(gameSession);

        //THEN
        assertTrue(gameSessionDb.getGameSessionList().contains(GameSession.builder().gameName("CloudAge").build()));
    }
}