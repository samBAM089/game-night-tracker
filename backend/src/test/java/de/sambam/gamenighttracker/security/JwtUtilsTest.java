package de.sambam.gamenighttracker.security;

import de.sambam.gamenighttracker.service.TimeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtUtilsTest {

    @Test
    public void generateJwtToken() {
        //GIVEN
        TimeUtils timeUtils = mock(TimeUtils.class);
        when(timeUtils.now()).thenReturn(Instant.ofEpochSecond(1616960003L));
        String secret = "super-secret";
        JwtConfig jwtConfig = mock(JwtConfig.class);
        when(jwtConfig.getJwtSecret()).thenReturn("super-secret");

        JwtUtils jwtUtils = new JwtUtils(timeUtils, jwtConfig);

        //WHEN
        String token = jwtUtils.createToken("sanne", new HashMap<>());

        //THEN
        Claims claims = Jwts.parser().setSigningKey("top-secret").parseClaimsJws(token).getBody();
        assertThat(claims.getSubject(), Matchers.is("sanne"));
        assertThat(claims.getIssuedAt(), Matchers.is(new Date(1616960003000L)));
        int fourHoursInMilliSeconds = 4 * 60 * 60 * 1000;
        assertThat(claims.getExpiration(), Matchers.is(new Date(1616960003000L + fourHoursInMilliSeconds)));

    }

}