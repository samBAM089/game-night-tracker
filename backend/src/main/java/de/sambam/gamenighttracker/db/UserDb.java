package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.Game;
import de.sambam.gamenighttracker.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDb {

    private List<User> userList;

    public UserDb() {
        this.userList = new ArrayList<>();
        this.userList.add(User.builder()
                .id("001")
                .userName("admin")
                .password("boardgamegeek")
                .playedGames(List.of(
                        Game.builder()
                                .releaseYear("2020")
                                .name("CloudAge")
                                .build()
                ))
                .build());
    }

    public void addUser(User userToAdd) {
        this.userList.add(userToAdd);
    }

    public User getLogInUser() {
        return this.userList.get(0);
    }

    public void clear() {
        this.userList.clear();
    }
}
