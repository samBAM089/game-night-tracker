package de.sambam.gamenighttracker.service;

import de.sambam.gamenighttracker.db.UserDb;
import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.GameSession;
import de.sambam.gamenighttracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            List<GameSession> sessionList = new ArrayList<>();
            for (Game game : gameList) {
                for (GameSession session : game.getGameSessionList()) {
                    sessionList.add(session);
                }
            }
            return sessionList;
        }
        return List.of();
    }
}
