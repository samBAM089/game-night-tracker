package de.sambam.gamenighttracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameSession {

    private String id;
    private String gameName;
    private String thumbnailUrl;
    private String date;
    private List<Player> playerList;
    private String duration;

}
