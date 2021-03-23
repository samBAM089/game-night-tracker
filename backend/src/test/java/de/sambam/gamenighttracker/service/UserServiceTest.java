package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UserServiceTest {

    UserDb userDb = mock(UserDb.class);

    @Test
    @DisplayName("listAllGames() should return all games from Db")
    public void listAllGamesTest() {
        //GIVEN
        when(userDb.getUser("1")).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("admin")
                .playedGames(List.of(
                        Game.builder()
                                .name("MauMau")
                                .build()))
                .build()));


        UserService userService = new UserService(userDb);

        //WHEN
        Optional<List<Game>> listOptional = userService.listAllGames("1");

        //THEN
        assertTrue(listOptional.equals(Optional.of(List.of(
                Game.builder()
                        .name("MauMau")
                        .build()))));

    }
}