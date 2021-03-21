package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.Game;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GameDb {
    private List<Game> gameList;

    public GameDb() {
        this.gameList = new ArrayList<>();

    }

    public Game add(Game newGame) {
        this.gameList.add(newGame);
        return newGame;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void clear() {
        this.gameList.clear();
    }

    public void delete(String gameToBeDeleted) {
        this.gameList = this.gameList.stream()
                .filter(game -> !game.getName().equals(gameToBeDeleted))
                .collect(Collectors.toList());
    }
    
}
