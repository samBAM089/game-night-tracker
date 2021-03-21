package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.GameSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameDbTest {

    @Test
    @DisplayName("getGameList should return all games from Db")
    public void getGameListTest() {
        //GIVEN
        GameDb gameDb = new GameDb();
        gameDb.add(Game.builder().name("Cubitos").releaseYear("2020").build());
        gameDb.add(Game.builder().name("CloudAge").releaseYear("2020").build());

        //WHEN
        List<Game> gameList = gameDb.getGameList();
        List<Game> expectedList = new ArrayList<>();
        expectedList.add(Game.builder().name("Cubitos").releaseYear("2020").build());
        expectedList.add(Game.builder().name("CloudAge").releaseYear("2020").build());

        //THEN
        assertTrue(gameList.containsAll(expectedList));
    }

    @Test
    @DisplayName("add() should add new game to Db")
    public void addNewGameTest() {
        //GIVEN
        GameDb gameDb = new GameDb();

        //WHEN
        Game newGame = Game.builder().name("Monopoly").releaseYear("1933").build();
        gameDb.add(newGame);

        //THEN
        assertTrue(gameDb.getGameList().contains(newGame));
    }

    @Test
    @DisplayName("delete() should remove player from Db")
    public void deleteTest() {
        //GIVEN
        GameDb gameDb = new GameDb();
        gameDb.add(Game.builder().name("Cubitos").releaseYear("2020").build());
        gameDb.add(Game.builder().name("CloudAge").releaseYear("2020").build());

        //WHEN
        String gameToDelete = "CloudAge";
        gameDb.delete(gameToDelete);
        List<Game> updatedGameList = gameDb.getGameList();

        //THEN
        assertTrue(!updatedGameList.contains(
                Game.builder().name("CloudAge").releaseYear("2020").build()
        ));
    }
    
}