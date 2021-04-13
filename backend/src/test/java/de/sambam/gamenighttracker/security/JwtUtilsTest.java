package de.sambam.gamenighttracker.security;

import de.sambam.gamenighttracker.service.TimeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtUtilsTest {

    private TimeUtils timeUtils = mock(TimeUtils.class);
    private JwtConfig jwtConfig = mock(JwtConfig.class);
    private JwtUtils jwtUtils = new JwtUtils(timeUtils, jwtConfig);


    @Test
    public void generateJwtToken() {
        //GIVEN
        Instant now = Instant.ofEpochSecond(Instant.now().getEpochSecond());
        when(timeUtils.now()).thenReturn(now);
        String secret = "super-secret";
        when(jwtConfig.getJwtSecret()).thenReturn(secret);

        JwtUtils jwtUtils = new JwtUtils(timeUtils, jwtConfig);

        //WHEN
        String token = jwtUtils.createToken("sanne", new HashMap<>(Map.of("data", "value")));

        //THEN
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        assertThat(claims.getSubject(), Matchers.is("sanne"));
        assertThat(claims.getIssuedAt(), Matchers.is(Date.from(now)));
        assertThat(claims.getExpiration(), Matchers.is(Date.from(now.plus(Duration.ofHours(4)))));

    }

}