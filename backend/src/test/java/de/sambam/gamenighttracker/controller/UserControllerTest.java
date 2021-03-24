package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.GameSession;
import de.sambam.gamenighttracker.model.Player;
import de.sambam.gamenighttracker.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserDb userDb;

    @BeforeEach
    public void setup() {
        Player player1 = Player.builder().name("Mario").color("red").score(123)
                .build();
        Player player2 = Player.builder().name("Sanne").color("blue").score(122)
                .build();
        Player player3 = Player.builder().name("samBAM").color("yellow").score(100)
                .build();
        Player player4 = Player.builder().name("Mario").color("red").score(90)
                .build();
        Player player5 = Player.builder().name("Sanne").color("blue").score(80)
                .build();
        Player player6 = Player.builder().name("samBAM").color("yellow").score(70)
                .build();
        GameSession session1 = GameSession.builder()
                .id("1").sessionState("DONE").winnerPlayerId("Mario")
                .playerList(List.of(player1, player2))
                .build();
        GameSession session2 = GameSession.builder()
                .id("2").sessionState("DONE").winnerPlayerId("samBAM")
                .playerList(List.of(player3, player4))
                .build();
        GameSession session3 = GameSession.builder()
                .id("3").sessionState("DONE").winnerPlayerId("Sanne")
                .playerList(List.of(player5, player6))
                .build();
        Game game1 = Game.builder()
                .name("Bonfire").releaseYear("2020").gameSessionList(List.of(session1))
                .build();
        Game game2 = Game.builder()
                .name("Calico").releaseYear("2020").gameSessionList(List.of(session2))
                .build();
        Game game3 = Game.builder()
                .name("Calico").releaseYear("2020").gameSessionList(List.of(session3))
                .build();

        userDb.deleteAll();
        userDb.save(User.builder()
                .id("1")
                .userName("Sanne")
                .playedGames(List.of(game1, game2, game3))
                .build()
        );
    }


    @Test
    @DisplayName("the GET request should return all the games from the Db")
    public void getAllGamesFromDbTest() {
        //GIVEN
        Player player1 = Player.builder().name("Mario").color("red").score(123)
                .build();
        Player player2 = Player.builder().name("Sanne").color("blue").score(122)
                .build();
        Player player3 = Player.builder().name("samBAM").color("yellow").score(100)
                .build();
        Player player4 = Player.builder().name("Mario").color("red").score(90)
                .build();
        Player player5 = Player.builder().name("Sanne").color("blue").score(80)
                .build();
        Player player6 = Player.builder().name("samBAM").color("yellow").score(70)
                .build();

        //WHEN
        ResponseEntity<Game[]> getResponse = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/user/games", Game[].class);
        Game[] gameList = getResponse.getBody();

        //THEN
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
        assertThat(gameList.length, is(3));
        assertThat(List.of(gameList), containsInAnyOrder(
                Game.builder()
                        .name("Bonfire")
                        .releaseYear("2020")
                        .gameSessionList(List.of(GameSession.builder()
                                .id("1")
                                .sessionState("DONE")
                                .winnerPlayerId("Mario")
                                .playerList(List.of(player1, player2))
                                .build()))
                        .build(),
                Game.builder()
                        .name("Calico")
                        .releaseYear("2020")
                        .gameSessionList(List.of(GameSession.builder()
                                .id("2")
                                .sessionState("DONE")
                                .winnerPlayerId("samBAM")
                                .playerList(List.of(player3, player4))
                                .build()))
                        .build(),
                Game.builder()
                        .name("Calico")
                        .releaseYear("2020")
                        .gameSessionList(List.of(GameSession.builder()
                                .id("3")
                                .sessionState("DONE")
                                .winnerPlayerId("Sanne")
                                .playerList(List.of(player5, player6))
                                .build()))
                        .build()));
    }

    @Test
    @DisplayName("GET request to /user/gamesessions should return all game sessions")
    public void getGameSessionsListTest() {
        //WHEN
        ResponseEntity<GameSession[]> getResponse = testRestTemplate.getForEntity(
                "http://localhost:" + port + "user/gamesessions", GameSession[].class);
        GameSession[] sessionList = getResponse.getBody();

        //THEN
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
        assertThat(sessionList.length, is(3));
        assertThat(List.of(sessionList), containsInAnyOrder(
                GameSession.builder()
                        .id("1").sessionState("DONE").winnerPlayerId("Mario")
                        .playerList(List.of(
                                Player.builder().name("Mario").color("red").score(123)
                                        .build(),
                                Player.builder().name("Sanne").color("blue").score(122)
                                        .build()))
                        .build(),
                GameSession.builder()
                        .id("2")
                        .sessionState("DONE")
                        .winnerPlayerId("samBAM")
                        .playerList(List.of(
                                Player.builder().name("samBAM").color("yellow").score(100)
                                        .build(),
                                Player.builder().name("Mario").color("red").score(90)
                                        .build()))
                        .build(),
                GameSession.builder()
                        .id("3")
                        .sessionState("DONE")
                        .winnerPlayerId("Sanne")
                        .playerList(List.of(
                                Player.builder().name("Sanne").color("blue").score(80)
                                        .build(),
                                Player.builder().name("samBAM").color("yellow").score(70)
                                        .build()))
                        .build()));

    }


}