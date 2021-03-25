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
            return gameList.stream().flatMap(game -> game.getGameSessionList().stream())
                    .collect(Collectors.toList());
        }
        return List.of();
    }


    public List<PlayerDto> getAllPlayersList(String id) {
        List<Game> gameList = userDb.findById(id).get().getPlayedGames();
        Map<String, PlayerDto> playerMap = new HashMap<>();
        List<Player> playerList = gameList.stream()
                .flatMap(game -> game.getGameSessionList().stream())
                .flatMap(session -> session.getPlayerList().stream())
                .collect(Collectors.toList());
        for (Player player : playerList) {
            playerMap.put(player.getName(), PlayerDto.builder()
                    .name(player.getName())
                    .color(player.getColor())
                    .build());
        }
        // playerList.stream().map(player -> playerMap.put(player.getName(), player));
        return playerMap.values().stream().collect(Collectors.toList());
    }

    /*public List<PlayerDto> listAllPlayers(String id) {
        Optional<User> user = userDb.findById(id);

        if (user.isPresent()) {
            List<Game> gameList = user.get().getPlayedGames();
            List<Player> playerList = getPlayerListFromGameList(gameList);
            List<String> nameList = getUniqueNamesFromPlayerList(playerList);
            List<PlayerDto> playerDtoList = buildPlayerDtoList(playerList, nameList);
            return playerDtoList;
        }
        return List.of();
    }

    private List<PlayerDto> buildPlayerDtoList(List<Player> playerList, List<String> nameList) {
        List<PlayerDto> playerDtoList = nameList.stream().map(name -> {
            Player match = playerList.stream()
                    .filter(player -> player.getName().equals(name))
                    .findFirst()
                    .orElse(null);
            return PlayerDto.builder().name(name).color(match.getColor()).build();
        }).collect(Collectors.toList());
        return playerDtoList;
    }

    private List<String> getUniqueNamesFromPlayerList(List<Player> playerList) {
        List<String> nameList = playerList.stream()
                .map(player -> player.getName())
                .distinct()
                .collect(Collectors.toList());
        return nameList;
    }

    private List<Player> getPlayerListFromGameList(List<Game> gameList) {
        List<Player> playerList = gameList.stream()
                .flatMap(game -> game.getGameSessionList().stream())
                .flatMap(session -> session.getPlayerList().stream())
                .collect(Collectors.toList());
        return playerList;
    }*/

    public Game addNewGameSession(Game newGame, String id, String apiGameId) {
        Optional<User> user = userDb.findById(id);

        if (user.isPresent()) {
            List<Game> gameList = user.get().getPlayedGames();
            Optional<Game> match = gameList.stream()
                    .filter(game -> game.getApiGameId().equals(newGame.getApiGameId()))
                    .findFirst();
            if (match.isPresent()) {
                addNewSessionToSessionList(newGame, id, apiGameId);
            } else {
                addNewGame(newGame, id);
            }
            return newGame;
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
    }

    private GameSession addNewSessionToSessionList(Game newGame, String id, String apiGameId) {
        User existingUser = userDb.findById(id).get();
        GameSession sessionToAdd = newGame.getGameSessionList().stream()
                .findFirst()
                .orElseThrow(null);
        List<Game> existingGameList = existingUser.getPlayedGames();
        Game match = existingGameList.stream()
                .filter(game -> game.getApiGameId().equals(apiGameId))
                .findFirst()
                .orElse(null);
        List<GameSession> existingGameSessionList = match.getGameSessionList();
        existingGameSessionList.add(sessionToAdd);
        userDb.save(existingUser);
        return sessionToAdd;


    }

    private Game addNewGame(Game newGame, String id) {

        User existingUser = userDb.findById(id).get();
        List<Game> existingGames = existingUser.getPlayedGames();
        existingGames.add(newGame);
        userDb.save(existingUser);
        return newGame;

    }
}
