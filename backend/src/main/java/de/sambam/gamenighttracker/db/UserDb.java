package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDb {

    private List<User> userList;

    public UserDb() {
        this.userList = new ArrayList<>(List.of(
                (User.builder()
                        .id("1")
                        .userName("admin")
                        .password("boardgamegeek")
                        .playedGames(List.of(
                                Game.builder()
                                        .releaseYear("2020")
                                        .name("CloudAge")
                                        .build()
                        ))
                        .build())));
    }

    public void addUser(User userToAdd) {
        this.userList.add(userToAdd);
    }

    public Optional<User> getUser(String id) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == id) {
                return Optional.of(userList.get(i));
            }
        }
        return Optional.empty();
    }


    public void clear() {
        this.userList.clear();
    }
}
