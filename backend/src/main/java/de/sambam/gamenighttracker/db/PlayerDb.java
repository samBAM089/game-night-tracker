package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerDb {

    private Player player1 = Player.builder().id("001").name("samBam").build();
    private Player player2 = Player.builder().id("002").name("Felix").build();
    private Player player3 = Player.builder().id("003").name("Andrea").build();

    private List<Player> playerList;

    public PlayerDb() {
        this.playerList = new ArrayList<>();
    }

    public void add(Player newPlayer) {
        this.playerList.add(newPlayer);
    }

    public List<Player> getPlayers() {
        return playerList;
    }

    public void clearDb() {
        playerList.clear();
    }
}
