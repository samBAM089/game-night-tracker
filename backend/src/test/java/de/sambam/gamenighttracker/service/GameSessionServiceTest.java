package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.GameDb;
import de.sambam.gamenighttracker.db.GameSessionDb;
import de.sambam.gamenighttracker.model.GameSession;
import de.sambam.gamenighttracker.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GameSessionServiceTest {

    GameSessionDb gameSessionDb = mock(GameSessionDb.class);

    @Test
    @DisplayName("addNewGameSession should add new game session to db")
    public void addNewGameSessionTest() {
        //GIVEN
        GameSessionService gameSessionService = new GameSessionService(gameSessionDb);

        //WHEN
        GameSession gameSessionToAdd = new GameSession();
        gameSessionService.addNewGameSession(gameSessionToAdd);

        //THEN
        verify(gameSessionDb).addGameSession(gameSessionToAdd);

    }
}