package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserDb userDb = mock(UserDb.class);

    @Test
    @DisplayName("listAllGames() should return all games from Db")
    public void listAllGamesTest() {
        //GIVEN
        when(userDb.getLogInUser()).thenReturn(User.builder()
                .id("001")
                .userName("admin")
                .password("boardgamegeek")
                .playedGames(List.of(
                        Game.builder()
                                .releaseYear("2020")
                                .name("CloudAge")
                                .build(),
                        Game.builder()
                                .releaseYear("1933")
                                .name("Monopoly")
                                .build()
                ))
                .build());

        UserService userService = new UserService(userDb);

        //WHEN
        List<Game> allTheGames = userService.listAllGames();

        //THEN
        assertTrue(allTheGames.containsAll(List.of(
                Game.builder().name("CloudAge").releaseYear("2020").build(),
                Game.builder().name("Monopoly").releaseYear("1933").build()
        )));
    }

}