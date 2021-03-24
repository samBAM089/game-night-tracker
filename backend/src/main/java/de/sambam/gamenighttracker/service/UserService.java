package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public List<PlayerDto> listAllPlayers(String id) {
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
    }

}

