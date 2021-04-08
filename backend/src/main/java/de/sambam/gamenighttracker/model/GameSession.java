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
    private String startDateTimeStamp;
    private int duration;
    private List<Player> playerList;
    private String winnerPlayerId;
}
