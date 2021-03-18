package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.Game;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GameDb {
    private final List<Game> gameList;

    public GameDb() {
        this.gameList = new ArrayList<>(List.of(
                Game.builder().name("Cubitos").releaseYear("2020").build(),
                Game.builder().name("CloudAge").releaseYear("2020").build(),
                Game.builder().name("Barrage").releaseYear("2019").build()
        ));
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
}
