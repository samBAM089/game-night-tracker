package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.PlayerDb;
import de.sambam.gamenighttracker.model.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private PlayerDb playerDb;

    @Autowired
    public PlayerService(PlayerDb playerDb) {
        this.playerDb = playerDb;
    }

    public void addPlayer(Player newPlayer) {
        playerDb.add(newPlayer);
    }

    public List<Player> listAllPlayers() {
        return playerDb.getPlayers();
    }
}
