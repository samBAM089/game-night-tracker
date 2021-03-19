package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.db.GameDb;
import de.sambam.gamenighttracker.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GameDb gameDb;

    @BeforeEach
    public void resetDatabase() {
        gameDb.clear();
    }


    @Test
    @DisplayName("post request should add new game to gameDb")
    public void addNewGameTest() {
        //GIVEN
        HttpEntity<Game> requestEntity = new HttpEntity<>(
                Game.builder().name("Scrabble").build());

        //WHEN
        ResponseEntity<Game> postResponse = restTemplate.exchange(
                "http://localhost:" + port + "/games", HttpMethod.POST, requestEntity, Game.class);

        //THEN
        assertThat(postResponse.getStatusCode(), is(HttpStatus.OK));
        assertEquals(postResponse.getBody(), Game.builder().name("Scrabble").build());
        assertTrue(gameDb.getGameList().contains(Game.builder().name("Scrabble").build()));

    }
}