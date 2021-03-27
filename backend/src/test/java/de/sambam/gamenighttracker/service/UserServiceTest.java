package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;


class UserServiceTest {

    private final UserDb userDb = mock(UserDb.class);
    private final UserService userService = new UserService(userDb);
    private final String userId = "1";

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

        //WHEN
        List<Game> list = userService.listAllGames(userId);

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

        //WHEN
        List<GameSession> gameSessionList = userService.listAllSessions(userId);

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
        Player player1 = Player.builder().name("Sanne").score(100).color("red").build();
        Player player2 = Player.builder().name("Tim").score(200).color("blue").build();
        Player player3 = Player.builder().name("John").score(300).color("yellow").build();
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

        //WHEN
        List<PlayerDto> actual = userService.listAllPlayers(userId);

        //THEN
        assertTrue(actual.equals(List.of(
                PlayerDto.builder()
                        .name("Sanne")
                        .color("red")
                        .build(),
                PlayerDto.builder()
                        .name("John")
                        .color("yellow")
                        .build(),
                PlayerDto.builder()
                        .name("Tim")
                        .color("blue")
                        .build())
        ));
    }


    @Test
    @DisplayName("addNewGame() should add a new game to the Db")
    public void addNewGameTest() {
        //GIVEN
        List<Game> gameList = new ArrayList<>();
        gameList.add(Game.builder()
                .apiGameId("123")
                .name("MauMau")
                .build());
        when(userDb.findById("1")).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("admin")
                .playedGames(gameList)
                .build()));

        Game gameToAdd = Game.builder()
                .apiGameId("234")
                .name("Monopoly")
                .build();

        //WHEN
        userService.addNewGame(gameToAdd, userId);
        List<Game> actual = userDb.findById(userId).get().getPlayedGames();

        //THEN
        assertThat(actual.size(), is(2));
        assertTrue(actual.contains(Game.builder()
                .apiGameId("234")
                .name("Monopoly")
                .build()));
    }


    @Test
    @DisplayName("addNewGameSession() should add new session to Db")
    public void addNewSessionTest() {
        //GIVEN
        Player player1 = Player.builder().name("Sanne").score(100).color("red").build();
        Player player2 = Player.builder().name("Tim").score(200).color("blue").build();
        Player player3 = Player.builder().name("John").score(300).color("yellow").build();
        List<Player> playerList1 = new ArrayList<>(List.of(player1, player2));
        List<Player> playerList2 = new ArrayList<>(List.of(player2, player3));
        GameSession session1 = GameSession.builder().playerList(playerList1).duration(90).build();
        GameSession session2 = GameSession.builder().playerList(playerList2).duration(50).build();
        GameSession session3 = GameSession.builder().playerList(playerList1).duration(150).build();
        List<GameSession> sessionList1 = new ArrayList<>();
        sessionList1.add(session1);
        sessionList1.add(session2);
        List<GameSession> sessionList2 = new ArrayList<>();
        sessionList2.add(session3);


        when(userDb.findById("1")).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("admin")
                .playedGames(List.of(
                        Game.builder()
                                .apiGameId("123")
                                .name("MauMau")
                                .gameSessionList(sessionList1)
                                .build(),
                        Game.builder()
                                .apiGameId("456")
                                .name("Monopoly")
                                .gameSessionList(sessionList2)
                                .build()))
                .build()));

        GameSession sessionToAdd = GameSession.builder()
                .playerList(playerList2)
                .duration(200)
                .build();
        String apiGameIdForMauMau = "123";

        //WHEN
        userService.addNewGameSession(sessionToAdd, userId, apiGameIdForMauMau);
        List<Game> gameList = userDb.findById(userId).get().getPlayedGames();
        Game mauMau = gameList.stream().filter(game -> game.getApiGameId().equals(apiGameIdForMauMau))
                .findFirst()
                .orElse(null);
        List<GameSession> sessionListOfMauMau = mauMau.getGameSessionList();

        //THEN
        assertThat(sessionListOfMauMau.size(), is(3));
        assertTrue(sessionListOfMauMau.contains(GameSession.builder()
                .playerList(playerList2)
                .duration(200)
                .build()));
    }
}
