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
import static org.mockito.Mockito.*;

class GameServiceTest {

    GameDb gameDb = mock(GameDb.class);

    @Test
    @DisplayName("addNewGame method should add a game to the Db")
    public void addNewGameTester() {
        //GIVEN
        GameService gameService = new GameService(gameDb);

        //WHEN
        Game gameToAdd = new Game();
        gameService.addNewGame(gameToAdd);

        //THEN
        verify(gameDb).add(gameToAdd);
    }

    @Test
    @DisplayName("listAllGames should return all the games from db")
    public void listAllGamesTest() {
        //GIVEN
        GameDb gameDb = mock(GameDb.class);
        when(gameDb.getGameList()).thenReturn(List.of(
                Game.builder().name("Cubitos").releaseYear("2020").build(),
                Game.builder().name("CloudAge").releaseYear("2020").build(),
                Game.builder().name("Barrage").releaseYear("2019").build(),
                Game.builder().name("CloudAge").releaseYear("2020").build()));

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


