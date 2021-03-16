package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PlayerDbTest {

    @Test
    @DisplayName("add method should new player to db")
    public void addNewPlayerToDbTest() {
        //GIVEN
        PlayerDb playerDb = new PlayerDb();
        Player player1 = Player.builder().id("001").name("samBAM").build();

        //WHEN
        playerDb.add(player1);
        List<Player> players = playerDb.getPlayers();
        Player expectedPlayer = Player.builder().id("001").name("samBAM").build();

        //THEN
        assertThat(players.contains(expectedPlayer), is(true));
    }
}