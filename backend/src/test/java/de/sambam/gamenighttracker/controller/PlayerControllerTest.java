package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.db.PlayerDb;
import de.sambam.gamenighttracker.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("post request should add new player to db")
class PlayerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PlayerDb playerDb;

    @BeforeEach
    public void resetDatabase() {
        playerDb.clearDb();
    }

    @Test
    @DisplayName("post endpoint should add new player to db")
    public void postNewPlayerTest() {

        //GIVEN
        HttpEntity<Player> requestEntity = new HttpEntity<>(
                Player.builder().id("009").name("Tom").build());

        //WHEN
        ResponseEntity<Player> postResponse = restTemplate.exchange(
                "http://localhost:" + port + "/players",
                HttpMethod.POST, requestEntity, Player.class);

        //THEN
        assertThat(postResponse.getStatusCode(), is(HttpStatus.OK));
        assertEquals(Player.builder().id("009").name("Tom").build(), postResponse.getBody());
        assertTrue(playerDb.getPlayers().contains(Player.builder().id("009").name("Tom").build()));
    }

}