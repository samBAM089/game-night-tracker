package de.sambam.gamenighttracker.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.*;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


class UserServiceTest {

    private final UserDb userDb = mock(UserDb.class);
    private final UuidGenerator uuidGenerator = mock(UuidGenerator.class);
    private final UserService userService = new UserService(userDb, uuidGenerator);
    private final String username = "sambam";

    @Test
    @DisplayName("listAllGames() should return all games from Db")
    public void listAllGamesTest() {
        //GIVEN
        when(userDb.findByUserName(username)).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("sambam")
                .playedGames(List.of(
                        Game.builder()
                                .name("MauMau")
                                .build()))
                .build()));

        //WHEN
        List<Game> list = userService.listAllGames(username);

        //THEN
        assertTrue(list.equals(List.of(
                Game.builder()
                        .name("MauMau")
                        .build())));
    }


    @Test
    @DisplayName("no gameList in user with id 1, listAllPlayers should return en empty List")
    public void userHasNoGameListReturnsEmptyListTest() {
        //GIVEN
        when(userDb.findByUserName(username)).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("sambam")
                .playedGames(List.of())
                .build()));

        //WHEN
        List<PlayerDto> playerList = userService.listAllPlayers(username);

        //THEN
        assertThat(playerList, is(List.of()));
    }

    @Test
    @DisplayName("no game in user with id 1, listAllPlayers should return en empty List")
    public void userHasNoGameReturnsEmptyListTest() {
        //GIVEN
        when(userDb.findByUserName(username)).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("sambam")
                .build()));

        //WHEN
        List<PlayerDto> playerList = userService.listAllPlayers(username);

        //THEN
        assertThat(playerList, is(List.of()));
    }

    @Test
    @DisplayName("no session in user with id 1, listAllPlayers should return en empty List")
    public void userHasNoSessionReturnsEmptyListTest() {
        //GIVEN
        when(userDb.findByUserName(username)).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("sambam")
                .playedGames(List.of(
                        Game.builder()
                                .name("Yatzee")
                                .releaseYear("1950")
                                .gameSessionList(List.of())
                                .build()
                ))
                .build()));

        //WHEN
        List<PlayerDto> playerList = userService.listAllPlayers(username);

        //THEN
        assertThat(playerList, is(List.of()));
    }

    @Test
    @DisplayName("no player in user with id 1, listAllPlayers should return en empty List")
    public void userHasNoPlayerReturnsEmptyListTest() {
        //GIVEN
        when(userDb.findByUserName(username)).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("sambam")
                .playedGames(List.of(
                        Game.builder()
                                .name("Yatzee")
                                .releaseYear("1950")
                                .gameSessionList(List.of(
                                        GameSession.builder()
                                                .sessionState("PREP")
                                                .playerList(List.of())
                                                .build()
                                ))
                                .build()
                ))
                .build()));

        //WHEN
        List<PlayerDto> playerList = userService.listAllPlayers(username);

        //THEN
        assertThat(playerList, is(List.of()));
    }

    @Test
    @DisplayName("no gameList in user with id 1 addNewGame should return empty optional")
    public void addNewGameWithNoGameListTest() {
        //GIVEN
        when(userDb.findByUserName(username)).thenReturn(Optional.of(
                User.builder()
                        .id("1")
                        .userName("sambam")
                        .build()));

        Game gameToAdd = Game.builder().name("Monopoly").build();
        //WHEN
        Optional<Game> newGame = userService.addNewGame(gameToAdd, username);

        //THEN
        assertTrue(newGame.equals(Optional.empty()));
    }

    @Test
    @DisplayName("listAllSessions() should return all game sessions sorted by the latest date")
    public void listAllSessionsTest() {
        //GIVEN
        when(userDb.findByUserName(username)).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("sambam")
                .playedGames(List.of(
                        Game.builder()
                                .name("MauMau")
                                .gameSessionList(List.of(
                                        GameSession.builder()
                                                .id("1")
                                                .startDateTimeStamp("2020-01-01-12:00")
                                                .sessionState("DONE")
                                                .winnerPlayerId("Sanne")
                                                .build(),
                                        GameSession.builder()
                                                .id("2")
                                                .startDateTimeStamp("2020-01-02-12:00")
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
                                                .startDateTimeStamp("2020-01-03-12:00")
                                                .sessionState("DONE")
                                                .winnerPlayerId("Hanno")
                                                .build()
                                ))
                                .build()))
                .build()));

        //WHEN
        List<GameSessionDto> sessionList = userService.listAllSessions(username);

        //THEN
        assertThat(sessionList.size(), is(3));
        assertTrue(sessionList.equals(List.of(
                GameSessionDto.builder()
                        .id("3")
                        .gameName("Monopoly")
                        .startDateTimestamp("2020-01-03-12:00")
                        .winnerPlayerId("Hanno")
                        .build(),
                GameSessionDto.builder()
                        .id("2")
                        .gameName("MauMau")
                        .startDateTimestamp("2020-01-02-12:00")
                        .winnerPlayerId("Mario")
                        .build(),
                GameSessionDto.builder()
                        .id("1")
                        .gameName("MauMau")
                        .startDateTimestamp("2020-01-01-12:00")
                        .winnerPlayerId("Sanne")
                        .build()
        )));
    }

    @Test
    @DisplayName("No sessions in user with id 1, listAllSessions should return empty list")
    public void listAllSessionsreturnsEmptyListTest() {
        //GIVEN
        when(userDb.findByUserName(username)).thenReturn(Optional.of(
                User.builder()
                        .id("1")
                        .userName("sambam")
                        .build()));

        //WHEN
        List<GameSessionDto> actual = userService.listAllSessions(username);

        //THEN
        assertTrue(actual.equals(List.of()));

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

        when(userDb.findByUserName(username)).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("sambam")
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
        List<PlayerDto> actual = userService.listAllPlayers(username);

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
        when(userDb.findByUserName(username)).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("sambam")
                .playedGames(gameList)
                .build()));

        Game gameToAdd = Game.builder()
                .apiGameId("234")
                .name("Monopoly")
                .build();

        when(uuidGenerator.generateUuiD()).thenReturn("777");

        //WHEN
        userService.addNewGame(gameToAdd, username);
        List<Game> actual = userDb.findByUserName(username).get().getPlayedGames();

        //THEN
        assertThat(actual.size(), is(2));
        assertTrue(actual.contains(Game.builder()
                .id("777")
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

        when(uuidGenerator.generateUuiD()).thenReturn("089");
        when(userDb.findByUserName(username)).thenReturn(Optional.of(User.builder()
                .id("1")
                .userName("sambam")
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
        userService.addNewGameSession(sessionToAdd, username, apiGameIdForMauMau);
        List<Game> gameList = userDb.findByUserName(username).get().getPlayedGames();
        Game mauMau = gameList.stream().filter(game -> game.getApiGameId().equals(apiGameIdForMauMau))
                .findFirst()
                .orElse(null);
        List<GameSession> sessionListOfMauMau = mauMau.getGameSessionList();

        //THEN
        assertThat(sessionListOfMauMau.size(), is(3));
        assertTrue(sessionListOfMauMau.contains(GameSession.builder()
                .id("089")
                .playerList(playerList2)
                .duration(200)
                .build()));
    }

    @Test
    @DisplayName("no gameList in user with id 1, addNewGameSession should return empty optional")
    public void addNewGameSessionWithoutGameList() {
        //GIVEN
        when(userDb.findByUserName(username)).thenReturn(Optional.of(
                User.builder()
                        .id("1")
                        .userName("sambam")
                        .build()));

        //WHEN
        GameSession sessionToAdd = GameSession.builder()
                .sessionState("DONE")
                .playerList(List.of())
                .build();
        String apiGameId = "123";
        Optional<GameSession> newSession = userService.addNewGameSession(sessionToAdd, username, apiGameId);

        //THEN
        assertTrue(newSession.equals(Optional.empty()));
    }

}
