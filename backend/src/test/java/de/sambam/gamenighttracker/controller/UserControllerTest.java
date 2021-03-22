package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.User;
import de.sambam.gamenighttracker.service.UserService;
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
import static org.hamcrest.Matchers.is;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserDb userDb;

    @BeforeEach
    public void resetDb() {
        userDb.clear();
    }

    @Test
    @DisplayName("the GET request should return all the games from the Db")
    public void getAllGamesFromDbTest() {
        //GIVEN
        userDb.addUser(User.builder()
                .id("003")
                .userName("Sanne")
                .playedGames(List.of(
                        Game.builder()
                                .name("Bonfire")
                                .releaseYear("2020")
                                .build(),
                        Game.builder()
                                .name("Calico")
                                .releaseYear("2020")
                                .build())
                )
                .build()
        );

        UserService userService = new UserService(userDb);

        //WHEN
        ResponseEntity<Game[]> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/user/games", Game[].class);
        Game[] gameList = getResponse.getBody();

        //THEN
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
        assertThat(gameList.length, is(2));
        assertTrue(userService.listAllGames().containsAll(List.of(
                Game.builder()
                        .name("Bonfire")
                        .releaseYear("2020")
                        .build(),
                Game.builder()
                        .name("Calico")
                        .releaseYear("2020")
                        .build())
        ));
    }
}