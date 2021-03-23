package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDbTest {

    @Test
    @DisplayName("getLogInUser() should return logged in user")
    public void getLogInUserTest() {
        //GIVEN
        UserDb userDb = new UserDb();
        userDb.addUser((User.builder()
                .id("1")
                .userName("admin")
                .password("boardgamegeek")
                .playedGames(List.of(
                        Game.builder()
                                .releaseYear("2020")
                                .name("CloudAge")
                                .build()
                ))
                .build()));

        //WHEN
        Optional<User> actual = userDb.getUser("1");

        //THEN
        assertTrue(actual.equals(Optional.of(User.builder()
                .id("1")
                .userName("admin")
                .password("boardgamegeek")
                .playedGames(List.of(
                        Game.builder()
                                .releaseYear("2020")
                                .name("CloudAge")
                                .build()
                ))
                .build())));
    }
}

