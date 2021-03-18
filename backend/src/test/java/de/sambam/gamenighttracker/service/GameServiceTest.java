package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.GameDb;
import de.sambam.gamenighttracker.model.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    @Test
    @DisplayName("addNewGame method should add a game to the Db")
    public void addNewGameTester() {
        //GIVEN
        GameDb gameDb = new GameDb();
        GameService gameService = new GameService(gameDb);

        //WHEN
        Game newGame = Game.builder().name("Monopoly").build();
        gameService.addNewGame(newGame);

        //THEN
        assertTrue(gameDb.getGameList().contains(Game.builder().name("Monopoly").build()));
    }
}