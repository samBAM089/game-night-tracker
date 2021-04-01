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

    public List<Game> listAllGames(String username) {
        Optional<User> user = userDb.findByUserName(username);
        if (user.isPresent()) {
            return user.get().getPlayedGames();
        }
        return List.of();
    }

    public List<GameSession> listAllSessions(String username) {
        Optional<User> user = userDb.findByUserName(username);
        if (user.isEmpty()) {
            return List.of();
        }
        List<Game> gameList = user.get().getPlayedGames();
        if (gameList == null) {
            return List.of();
        }
        return gameList.stream().filter(game -> game != null)
                .flatMap(game -> game.getGameSessionList().stream())
                .filter(session -> session != null)
                .collect(Collectors.toList());
    }


    public List<PlayerDto> listAllPlayers(String username) {
        List<Game> gameList = getGamesForUser(username);
        Map<String, PlayerDto> playerList = new HashMap<>();
        for (Game game : gameList) {
            getPlayersForOneGame(game).forEach(player ->
                    playerList.put(player.getName(), PlayerDto.builder()
                            .name(player.getName())
                            .color(player.getColor())
                            .build()));
        }
        return playerList.values().stream().collect(Collectors.toList());
    }

    private List<Player> getPlayersForOneGame(Game game) {
        List<Player> playerList = new ArrayList<>();

        if (game == null) {
            return playerList;
        }
        List<GameSession> existingSessionList = game.getGameSessionList();
        if (existingSessionList == null) {
            return playerList;
        }
        for (GameSession session : existingSessionList) {
            if (session != null) {
                List<Player> existingPlayerList = session.getPlayerList();
                if (existingPlayerList != null)
                    for (Player player : existingPlayerList) {
                        if (player != null) {
                            playerList.add(player);
                        }
                    }
            }
        }
        return playerList;
    }

    private List<Game> getGamesForUser(String username) {
        Optional<User> user = userDb.findByUserName(username);

        if (user.isEmpty()) {
            return List.of();
        }
        List<Game> gameList = user.get().getPlayedGames();
        if (gameList == null) {
            return List.of();
        }
        return gameList;
    }


    public Optional<Game> addNewGame(Game newGame, String username) {

        Optional<User> user = userDb.findByUserName(username);

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

    public Optional<GameSession> addNewGameSession(GameSession newSession, String username, String
            apiGameId) {
        Optional<User> user = userDb.findByUserName(username);

        if (user.isPresent()) {
            List<Game> gameList = user.get().getPlayedGames();
            if (gameList != null) {
                Optional<Game> match = gameList.stream()
                        .filter(game -> game.getApiGameId().equals(apiGameId))
                        .findFirst();
                if (match.isPresent()) {

                    List<GameSession> sessionList = match.get().getGameSessionList();
                    if (sessionList != null) {
                        sessionList.add(newSession);
                        userDb.save(user.get());
                        return Optional.of(newSession);
                    }
                }
            }
        }
        return Optional.empty();
    }

}

