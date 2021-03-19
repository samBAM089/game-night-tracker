package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.GameDb;
import de.sambam.gamenighttracker.model.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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

    @Test
    @DisplayName("listAllGames should return all the games from db")
    public void listAllGamesTest() {
        //GIVEN
        GameDb gameDb = new GameDb();
        gameDb.add(Game.builder().name("CloudAge").releaseYear("2020").build());
        GameService gameService = new GameService(gameDb);

        //WHEN
        List<Game> allTheGames = gameService.listAllGames();
        List<Game> expectedList = new ArrayList<>(List.of(
                Game.builder().name("Cubitos").releaseYear("2020").build(),
                Game.builder().name("CloudAge").releaseYear("2020").build(),
                Game.builder().name("Barrage").releaseYear("2019").build(),
                Game.builder().name("CloudAge").releaseYear("2020").build()
        ));

        //THEN
        assertThat(allTheGames, is(expectedList));
    }
}


