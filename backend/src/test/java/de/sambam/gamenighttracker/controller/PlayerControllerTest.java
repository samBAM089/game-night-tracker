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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Testing all available requests to the backend")
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
    @DisplayName("get endpoint should show all players in db")
    public void getAllPlayers() {
        //GIVEN
        playerDb.add(Player.builder().id("001").name("Richard").build());
        playerDb.add(Player.builder().id("002").name("Tom").build());

        //WHEN
        ResponseEntity<Player[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/players", Player[].class);
        HttpStatus statusCode = response.getStatusCode();
        Player[] players = response.getBody();

        //THEN
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(playerDb.getPlayers().containsAll(List.of(
                Player.builder().id("001").name("Richard").build(),
                Player.builder().id("002").name("Tom").build()
        )));
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