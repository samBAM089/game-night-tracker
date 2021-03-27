package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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
            if (gameList != null) {
                return gameList.stream()
                        .filter(game -> game != null)
                        .flatMap(game -> game.getGameSessionList().stream())
                        .collect(Collectors.toList());
            }
        }
        return List.of();
    }

    public List<PlayerDto> listAllPlayers(String id) {
        Optional<User> user = userDb.findById(id);
        if (user.isPresent()) {
            List<Game> gameList = userDb.findById(id).get().getPlayedGames();
            Map<String, PlayerDto> playerMap = new HashMap<>();
            if (gameList != null) {
                List<Player> playerList = gameList.stream()
                        .filter(game -> game != null)
                        .flatMap(game -> game.getGameSessionList().stream())
                        .filter(session -> session != null)
                        .flatMap(session -> session.getPlayerList().stream())
                        .filter(player -> player != null)
                        .collect(Collectors.toList());
                for (Player player : playerList) {
                    if (player != null) {
                        playerMap.put(player.getName(), PlayerDto.builder()
                                .name(player.getName())
                                .color(player.getColor())
                                .build());
                    }
                }
                return playerMap.values().stream().collect(Collectors.toList());
            }
        }
        return List.of();
    }

    public Optional<Game> addNewGame(Game newGame, String id) {

        Optional<User> user = userDb.findById(id);
        if (user.isPresent()) {
            List<Game> existingGames = user.get().getPlayedGames();
            if (existingGames != null) {
                existingGames.add(newGame);
                userDb.save(user.get());
                return Optional.of(newGame);
            }
        }
        return Optional.empty();
    }

    public Optional<GameSession> addNewGameSession(GameSession newSession, String id, String apiGameId) {
        Optional<User> user = userDb.findById(id);

        if (user.isPresent()) {
            List<Game> gameList = user.get().getPlayedGames();
            if (gameList != null) {
                Optional<Game> match = gameList.stream()
                        .filter(game -> game.getApiGameId().equals(apiGameId))
                        .findFirst();
                if (match.isPresent()) {
                    match.get().getGameSessionList().add(newSession);
                    userDb.save(user.get());
                    return Optional.of(newSession);
                }
            }
        }
        return Optional.empty();
    }

}
