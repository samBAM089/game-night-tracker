package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;


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
        List<Game> list = userService.listAllGames("1");

        //THEN
        assertTrue(list.equals(List.of(
                Game.builder()
                        .name("MauMau")
                        .build())));

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
        List<GameSession> gameSessionList = userService.listAllSessions("1");

        //THEN
        assertTrue(gameSessionList.equals(List.of(
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
        )));
    }

    @Test
    @DisplayName("listAllPlayers() should list all players without duplicates")
    public void listAllPlayersTest() {
        //GIVEN
        Player player1 = Player.builder()
                .name("Sanne")
                .score(100)
                .color("red")
                .build();
        Player player2 = Player.builder()
                .name("Tim")
                .score(200)
                .color("blue")
                .build();
        Player player3 = Player.builder()
                .name("John")
                .score(300)
                .color("yellow")
                .build();
        List<Player> playerList1 = new ArrayList<>(List.of(player1, player2));
        List<Player> playerList2 = new ArrayList<>(List.of(player2, player3));

        when(userDb.findById("1")).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("admin")
                .playedGames(List.of(
                        Game.builder()
                                .name("MauMau")
                                .gameSessionList(List.of(
                                        GameSession.builder()
                                                .id("1")
                                                .playerList(playerList1)
                                                .build(),
                                        GameSession.builder()
                                                .id("2")
                                                .playerList(playerList2)
                                                .build()
                                ))
                                .build(),
                        Game.builder()
                                .name("Monopoly")
                                .gameSessionList(List.of(
                                        GameSession.builder()
                                                .id("3")
                                                .playerList(playerList1)
                                                .build()
                                ))
                                .build()))
                .build()));

        UserService userService = new UserService(userDb);

        //WHEN
        List<PlayerDto> actual = userService.listAllPlayers("1");

        //THEN
        assertTrue(actual.equals(List.of(
                PlayerDto.builder()
                        .name("Sanne")
                        .color("red")
                        .build(),
                PlayerDto.builder()
                        .name("Tim")
                        .color("blue")
                        .build(),
                PlayerDto.builder()
                        .name("John")
                        .color("yellow")
                        .build())
        ));
    }
}