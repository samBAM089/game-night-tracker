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

    public List<Game> listAllGames(String userId) {
        Optional<User> user = userDb.findById(userId);
        if (user.isPresent()) {
            return user.get().getPlayedGames();
        }
        return List.of();
    }

    public List<GameSession> listAllSessions(String userId) {
        Optional<User> user = userDb.findById(userId);
        List<GameSession> sessionList = new ArrayList<>();
        if (user.isPresent()) {
            List<Game> gameList = user.get().getPlayedGames();
            if (gameList != null) {
                for (Game game : gameList) {
                    if (game != null) {
                        List<GameSession> existingSessionList = game.getGameSessionList();
                        for (GameSession session : existingSessionList) {
                            if (session != null) {
                                sessionList.add(session);
                            }
                        }
                    }
                }
            }
        }
        return sessionList;
    }

    public List<PlayerDto> listAllPlayers(String userId) {
        Optional<User> user = userDb.findById(userId);
        Map<String, PlayerDto> playerList = new HashMap<>();

        if (user.isPresent()) {
            List<Game> gameList = user.get().getPlayedGames();
            if (gameList != null) {
                for (Game game : gameList) {
                    if (game != null) {
                        List<GameSession> existingSessionList = game.getGameSessionList();
                        if (existingSessionList != null) {
                            for (GameSession session : existingSessionList) {
                                if (session != null) {
                                    List<Player> existingPlayerList = session.getPlayerList();
                                    if (existingPlayerList != null)
                                        for (Player player : existingPlayerList) {
                                            if (player != null) {
                                                playerList.put(player.getName(), PlayerDto.builder()
                                                        .name(player.getName())
                                                        .color(player.getColor())
                                                        .build());

                                            }
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
        return playerList.values().stream().collect(Collectors.toList());
    }


    public Optional<Game> addNewGame(Game newGame, String userId) {

        Optional<User> user = userDb.findById(userId);

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

    public Optional<GameSession> addNewGameSession(GameSession newSession, String userId, String apiGameId) {
        Optional<User> user = userDb.findById(userId);

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
