package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDbTest {

    @Test
    @DisplayName("getLogInUser() should return logged in user")
    public void getLogInUserTest() {
        //GIVEN
        UserDb userDb = new UserDb();

        //WHEN
        User actual = userDb.getLogInUser();

        //THEN
        assertTrue(actual.equals(User.builder()
                .id("001")
                .userName("admin")
                .password("boardgamegeek")
                .playedGames(List.of(
                        Game.builder()
                                .releaseYear("2020")
                                .name("CloudAge")
                                .build()
                ))
                .build()));
    }
}

