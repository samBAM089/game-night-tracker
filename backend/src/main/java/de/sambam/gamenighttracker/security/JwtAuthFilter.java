package de.sambam.gamenighttracker.security;

import de.sambam.gamenighttracker.service.TimeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.Jwts.parser;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtConfig jwtConfig;
    private TimeUtils timeUtils;

    public JwtAuthFilter(JwtConfig jwtConfig, TimeUtils timeUtils) {
        this.jwtConfig = jwtConfig;
        this.timeUtils = timeUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null) {
                Jws<Claims> parsedToken = parseToken(authorizationHeader);
                if (parsedToken.getBody().getExpiration().after(Date.from(timeUtils.now()))) {
                    setSecurityContext(parsedToken);
                }
            }
        } catch (Exception e) {
            logger.warn("failed to parse token", e);
        }
        filterChain.doFilter(request, response);
    }

    private Jws<Claims> parseToken(String authorizationHeader) {
        String token = authorizationHeader.replace("bearer", "").trim();
        Jws<Claims> parsedToken = Jwts.parser().setSigningKey(jwtConfig.getJwtSecret()).parseClaimsJws(token);
        return parsedToken;
    }

    private void setSecurityContext(Jws<Claims> parsedToken) {
        Claims body = parsedToken.getBody();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                body.getSubject(), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
