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
        userDb.deleteAll();
        userDb.save(User.builder()
                .id("1")
                .userName("Sanne")
                .playedGames(List.of(
                        Game.builder()
                                .name("Bonfire")
                                .releaseYear("2020")
                                .gameSessionList(List.of(
                                        GameSession.builder()
                                                .id("1")
                                                .sessionState("DONE")
                                                .winnerPlayerId("Mario")
                                                .playerList(List.of(
                                                        Player.builder()
                                                                .name("Mario")
                                                                .color("red")
                                                                .score(123)
                                                                .build(),
                                                        Player.builder()
                                                                .name("Sanne")
                                                                .color("blue")
                                                                .score(122)
                                                                .build()
                                                ))
                                                .build()
                                ))
                                .build(),
                        Game.builder()
                                .name("Calico")
                                .releaseYear("2020")
                                .gameSessionList(List.of(
                                        GameSession.builder()
                                                .id("2")
                                                .sessionState("DONE")
                                                .winnerPlayerId("Mario")
                                                .playerList(List.of(
                                                        Player.builder()
                                                                .name("Mario")
                                                                .color("red")
                                                                .score(10)
                                                                .build(),
                                                        Player.builder()
                                                                .name("Sanne")
                                                                .color("blue")
                                                                .score(9)
                                                                .build()
                                                ))
                                                .build()
                                ))
                                .build())
                )
                .build()
        );
    }


    @Test
    @DisplayName("the GET request should return all the games from the Db")
    public void getAllGamesFromDbTest() {
        //GIVEN

        //WHEN
        ResponseEntity<Game[]> getResponse = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/user/games", Game[].class);
        Game[] gameList = getResponse.getBody();

        //THEN
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
        assertThat(gameList.length, is(2));
        assertThat(List.of(gameList), containsInAnyOrder(
                Game.builder()
                        .name("Bonfire")
                        .releaseYear("2020")
                        .build(),
                Game.builder()
                        .name("Calico")
                        .releaseYear("2020")
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
        assertThat(sessionList.length, is(2));
        assertThat(List.of(sessionList), containsInAnyOrder(
                GameSession.builder()
                        .id("1")
                        .sessionState("DONE")
                        .winnerPlayerId("Mario")
                        .playerList(List.of(
                                Player.builder()
                                        .name("Mario")
                                        .color("red")
                                        .score(123)
                                        .build(),
                                Player.builder()
                                        .name("Sanne")
                                        .color("blue")
                                        .score(122)
                                        .build()))
                        .build(),
                GameSession.builder()
                        .id("2")
                        .sessionState("DONE")
                        .winnerPlayerId("Mario")
                        .playerList(List.of(
                                Player.builder()
                                        .name("Mario")
                                        .color("red")
                                        .score(10)
                                        .build(),
                                Player.builder()
                                        .name("Sanne")
                                        .color("blue")
                                        .score(9)
                                        .build()))
                        .build()));
    }

}