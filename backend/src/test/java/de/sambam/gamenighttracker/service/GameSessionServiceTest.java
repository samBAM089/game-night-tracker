package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.GameSessionDb;
import de.sambam.gamenighttracker.model.GameSession;
import de.sambam.gamenighttracker.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameSessionServiceTest {

    @Test
    @DisplayName("addNewGameSession should add new game session to db")
    public void addNewGameSessionTest() {
        //GIVEN
        GameSessionDb gameSessionDb = new GameSessionDb();
        GameSessionService gameSessionService = new GameSessionService(gameSessionDb);
        GameSession gameSessionToAdd = new GameSession();
        gameSessionToAdd = GameSession.builder().gameName("CloudAge").playerList(List.of(
                Player.builder().name("samBAM").build(),
                Player.builder().name("Susanne").build()
        )).build();

        //WHEN
        gameSessionService.addNewGameSession(gameSessionToAdd);

        //THEN
        assertTrue(gameSessionDb.getGameSessionList().contains(
                GameSession.builder().gameName("CloudAge").playerList(List.of(
                        Player.builder().name("samBAM").build(),
                        Player.builder().name("Susanne").build()
                )).build()
        ));

    }
}