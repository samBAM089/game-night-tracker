package de.sambam.gamenighttracker.controller;

import de.sambam.gamenighttracker.model.LoginDto;
import de.sambam.gamenighttracker.security.AppUser;
import de.sambam.gamenighttracker.security.AppUserDb;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

    @LocalServerPort
    private int port;

    private String getUrl() {
        return "http://localhost:" + port + "auth/login";
    }

    @Autowired
    private AppUserDb userDb;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("POST method on login endpoint should return a JWT token")
    public void loginReturnsJwtTokenTest() {
        //GIVEN
        String username = "sanne";
        String password = "secret";

        String encode = passwordEncoder.encode((password));
        userDb.save(new AppUser(username, encode));

        //WHEN
        ResponseEntity<String> response = restTemplate.postForEntity(getUrl(), new LoginDto(username, password), String.class);

        //THEN
        assertThat(response.getStatusCode(), Matchers.is(HttpStatus.OK));
        Claims claims = Jwts.parser().setSigningKey("top-secret").parseClaimsJws(response.getBody()).getBody();
        assertThat(claims.getSubject(), Matchers.is("sanne"));
    }

    @Test
    @DisplayName("POST method on login endpoint with wrong credentials should return bad request")
    public void loginWithWrongCredentialsTest() {
        //GIVEN
        String username = "sanne";
        String password = "secret";

        String encode = passwordEncoder.encode((password));
        userDb.save(new AppUser(username, encode));

        //WHEN
        ResponseEntity<String> response = restTemplate.postForEntity(
                getUrl(), new LoginDto(username, "wrongPassword"), String.class);

        //THEN
        assertThat(response.getStatusCode(), Matchers.is(HttpStatus.BAD_REQUEST));
    }

}