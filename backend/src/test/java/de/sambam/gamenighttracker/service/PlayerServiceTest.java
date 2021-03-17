package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.PlayerDb;
import de.sambam.gamenighttracker.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PlayerServiceTest {


    @Test
    @DisplayName("addPlayer should add new player to playerDb")
    public void addPlayerTester() {
        //GIVEN
        PlayerDb playerDb = new PlayerDb();
        playerDb.add(Player.builder().id("001").name("samBAM").build());
        playerDb.add(Player.builder().id("002").name("Andrea").build());
        

        PlayerService playerService = new PlayerService(playerDb);

        //WHEN
        playerService.addPlayer(Player.builder().id("003").name("Thomas").build());
        List<Player> playerList = playerDb.getPlayers();

        //THEN
        assertTrue(playerList.contains(Player.builder().id("003").name("Thomas").build()));

    }
}