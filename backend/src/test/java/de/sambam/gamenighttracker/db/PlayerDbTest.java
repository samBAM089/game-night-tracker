package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerDbTest {

    @Test
    @DisplayName("add method should add new player to db")
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

    @Test
    @DisplayName("getPlayers should return all player objects")
    public void getPlayersTest() {
        //GIVEN
        PlayerDb playerDb = new PlayerDb();
        playerDb.add(Player.builder().id("001").name("samBAM").build());
        playerDb.add(Player.builder().id("002").name("Andrea").build());
        playerDb.add(Player.builder().id("003").name("Tim").build());

        //WHEN
        List<Player> playerList = playerDb.getPlayers();
        List<Player> expectedList = new ArrayList<>(List.of(
                Player.builder().id("001").name("samBAM").build(),
                Player.builder().id("002").name("Andrea").build(),
                Player.builder().id("003").name("Tim").build()
        ));

        //THEN
        assertTrue(playerList.containsAll(expectedList));
    }

}
