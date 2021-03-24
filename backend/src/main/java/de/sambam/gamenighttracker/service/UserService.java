package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.GameSession;
import de.sambam.gamenighttracker.model.Player;
import de.sambam.gamenighttracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDb userDb;

    @Autowired
    public UserService(UserDb userDb) {
        this.userDb = userDb;
    }

    public List<Game> listAllGames(String id) {
        Optional<User> user = userDb.findById(id);
        if (user.isPresent()) {
            return user.get().getPlayedGames();
        }
        return List.of();
    }

    public List<GameSession> listAllSessions(String id) {
        Optional<User> user = userDb.findById(id);
        if (user.isPresent()) {
            List<Game> gameList = user.get().getPlayedGames();
            return gameList.stream().flatMap(game -> game.getGameSessionList().stream())
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public List<Player> listAllPlayers(String id) {
        Optional<User> user = userDb.findById(id);
        List<Game> gameList = user.get().getPlayedGames();
        if (user.isPresent()) {
            List<GameSession> sessionList = gameList.stream().flatMap(game -> game.getGameSessionList().stream())
                    .collect(Collectors.toList());
            List<Player> playerList = sessionList.stream().flatMap(session -> session.getPlayerList().stream())
                    .collect(Collectors.toList());
            return playerList.stream().distinct().collect(Collectors.toList());
        }
        return List.of();
    }

}

