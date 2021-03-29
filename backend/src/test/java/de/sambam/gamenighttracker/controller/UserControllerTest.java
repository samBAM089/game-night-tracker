package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.*;
import de.sambam.gamenighttracker.security.AppUser;
import de.sambam.gamenighttracker.security.AppUserDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
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

    @Autowired
    private AppUserDb appUserDb;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .apiGameId("123").name("Bonfire").releaseYear("2020").gameSessionList(List.of(session1))
                .build();
        Game game2 = Game.builder()
                .apiGameId("456").name("Calico").releaseYear("2020").gameSessionList(List.of(session2))
                .build();
        Game game3 = Game.builder()
                .apiGameId("789").name("Calico").releaseYear("2020").gameSessionList(List.of(session3))
                .build();

        userDb.deleteAll();
        appUserDb.deleteAll();
        userDb.save((User.builder()
                .userName("sanne")
                .playedGames(Arrays.asList(game1, game2, game3))
                .build()));
    }


    @Test
    @DisplayName("GET request to /user/games should return all the games from the Db")
    public void getGameListTest() {
        //GIVEN

        //WHEN
        String jwtToken = logintoApp();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(header);

        ResponseEntity<Game[]> getResponse = testRestTemplate.exchange(
                "http://localhost:" + port + "/user/games", HttpMethod.GET, entity, Game[].class);
        Game[] gameList = getResponse.getBody();

        //THEN
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
        assertThat(gameList.length, is(3));
        assertThat(List.of(gameList), containsInAnyOrder(
                Game.builder()
                        .apiGameId("123")
                        .name("Bonfire")
                        .releaseYear("2020")
                        .gameSessionList(List.of(GameSession.builder()
                                .id("1")
                                .sessionState("DONE")
                                .winnerPlayerId("Mario")
                                .playerList(List.of(
                                        Player.builder().name("Mario").color("red").score(123)
                                                .build(),
                                        Player.builder().name("Sanne").color("blue").score(122)
                                                .build()
                                ))
                                .build()))
                        .build(),
                Game.builder()
                        .apiGameId("456")
                        .name("Calico")
                        .releaseYear("2020")
                        .gameSessionList(List.of(GameSession.builder()
                                .id("2")
                                .sessionState("DONE")
                                .winnerPlayerId("samBAM")
                                .playerList(List.of(
                                        Player.builder().name("samBAM").color("yellow").score(100)
                                                .build(),
                                        Player.builder().name("Mario").color("red").score(90)
                                                .build()
                                ))
                                .build()))
                        .build(),
                Game.builder()
                        .apiGameId("789")
                        .name("Calico")
                        .releaseYear("2020")
                        .gameSessionList(List.of(GameSession.builder()
                                .id("3")
                                .sessionState("DONE")
                                .winnerPlayerId("Sanne")
                                .playerList(List.of(
                                        Player.builder().name("Sanne").color("blue").score(80)
                                                .build(),
                                        Player.builder().name("samBAM").color("yellow").score(70)
                                                .build()
                                ))
                                .build()))
                        .build()));
    }


    @Test
    @DisplayName("GET request to /user/players should return all players from Db w/o duplicates")
    public void getPlayerListTest() {
        //WHEN
        String jwtToken = logintoApp();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(header);
        ResponseEntity<PlayerDto[]> getResponse = testRestTemplate.exchange(
                "http://localhost:" + port + "/user/players", HttpMethod.GET, entity, PlayerDto[].class);
        PlayerDto[] playerDtoList = getResponse.getBody();

        //THEN
        assertTrue(getResponse.getStatusCode().equals(HttpStatus.OK));
        assertThat(getResponse.getBody().length, is(3));
        assertThat(List.of(playerDtoList), containsInAnyOrder(
                PlayerDto.builder().name("Mario").color("red")
                        .build(),
                PlayerDto.builder().name("Sanne").color("blue")
                        .build(),
                PlayerDto.builder().name("samBAM").color("yellow")
                        .build()
        ));
    }


    @Test
    @DisplayName("POST request to /user/game should add new game to Db")
    public void addNewGameTest() {
        //GIVEN
        Game gameToAdd = Game.builder().name("Tabu").releaseYear("1990").build();

        //WHEN
        String jwtToken = logintoApp();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(jwtToken);
        HttpEntity<Game> entity = new HttpEntity<>(gameToAdd, header);
        ResponseEntity<Game> postResponse = testRestTemplate.exchange(
                "http://localhost:" + port + "user/game", HttpMethod.POST, entity, Game.class);

        //THEN
        assertThat(postResponse.getStatusCode(), is(HttpStatus.OK));
        assertEquals(postResponse.getBody(), Game.builder()
                .name("Tabu").releaseYear("1990")
                .build());
    }

    @Test
    @DisplayName("POST request to /user/game/{apiGameId}/gamesession should add new session to Db")
    public void addNewGameSessionTest() {
        //GIVEN
        String apiGameId = "123";
        GameSession sessionToAdd = GameSession.builder().sessionState("DONE").winnerPlayerId("samBAM").build();


        //WHEN
        String jwtToken = logintoApp();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(jwtToken);
        HttpEntity<GameSession> entity = new HttpEntity<>(sessionToAdd, header);
        ResponseEntity<GameSession> postResponse = testRestTemplate.exchange(
                "http://localhost:" + port + "user/game/" + apiGameId + "/gamesessions",
                HttpMethod.POST, entity, GameSession.class);

        //THEN
        assertThat(postResponse.getStatusCode(), is(HttpStatus.OK));
        assertEquals(postResponse.getBody(), GameSession.builder()
                .sessionState("DONE").winnerPlayerId("samBAM")
                .build());
    }


    private String logintoApp() {
        String password = passwordEncoder.encode("superSecretPassword");
        appUserDb.save(new AppUser("sanne", password));
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/auth/login",
                new LoginDto("sanne", "superSecretPassword"),
                String.class);
        return loginResponse.getBody();
    }
}