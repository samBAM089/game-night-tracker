package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.GameSession;
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
        when(userDb.findById("1")).thenReturn(Optional.of(User.builder()
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

    @Test
    @DisplayName("listAllSessions() should return all game sessions")
    public void listAllSessionsTest() {
        //GIVEN
        when(userDb.findById("1")).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("admin")
                .playedGames(List.of(
                        Game.builder()
                                .name("MauMau")
                                .gameSessionList(List.of(
                                        GameSession.builder()
                                                .id("1")
                                                .sessionState("DONE")
                                                .winnerPlayerId("Sanne")
                                                .build(),
                                        GameSession.builder()
                                                .id("2")
                                                .sessionState("DONE")
                                                .winnerPlayerId("Mario")
                                                .build()
                                ))
                                .build(),
                        Game.builder()
                                .name("Monopoly")
                                .gameSessionList(List.of(
                                        GameSession.builder()
                                                .id("3")
                                                .sessionState("DONE")
                                                .winnerPlayerId("Hanno")
                                                .build()
                                ))
                                .build()))
                .build()));

        UserService userService = new UserService(userDb);

        //WHEN
        Optional<List<GameSession>> gameSessionList = userService.listAllSessions("1");

        //THEN
        assertTrue(gameSessionList.equals(Optional.of(List.of(
                GameSession.builder()
                        .id("1")
                        .sessionState("DONE")
                        .winnerPlayerId("Sanne")
                        .build(),
                GameSession.builder()
                        .id("2")
                        .sessionState("DONE")
                        .winnerPlayerId("Mario")
                        .build(),
                GameSession.builder()
                        .id("3")
                        .sessionState("DONE")
                        .winnerPlayerId("Hanno")
                        .build()
        ))));
    }
}