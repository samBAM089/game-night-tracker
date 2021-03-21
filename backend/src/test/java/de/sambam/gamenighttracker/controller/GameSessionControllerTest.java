package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.db.GameSessionDb;
import de.sambam.gamenighttracker.model.GameSession;
import de.sambam.gamenighttracker.model.Player;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameSessionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    GameSessionDb gameSessionDb;

    @BeforeEach
    public void resetDb() {
        gameSessionDb.clear();
    }

    @Test
    @Description("post endpoint should add new game session to db")
    public void addNewGameSessionTest() {
        //GIVEN
        HttpEntity<GameSession> requestEntity = new HttpEntity<>(
                GameSession.builder().gameName("CloudAge").playerList(List.of(
                        Player.builder().name("samBAM").build(),
                        Player.builder().name("Susanne").build()
                )).build()
        );

        //WHEN
        ResponseEntity<GameSession> postResponse = restTemplate.exchange(
                "http://localhost:" + port + "/gamesessions", HttpMethod.POST, requestEntity, GameSession.class);


        //THEN
        assertThat(postResponse.getStatusCode(), is(HttpStatus.OK));
        assertEquals(postResponse.getBody(), GameSession.builder().gameName("CloudAge").playerList(List.of(
                Player.builder().name("samBAM").build(),
                Player.builder().name("Susanne").build())).build());
        assertTrue(gameSessionDb.getGameSessionList().contains(
                GameSession.builder().gameName("CloudAge").playerList(List.of(
                        Player.builder().name("samBAM").build(),
                        Player.builder().name("Susanne").build())).build()));

    }


}