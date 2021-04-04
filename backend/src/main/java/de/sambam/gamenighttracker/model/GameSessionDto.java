package de.sambam.gamenighttracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameSessionDto implements Comparable<GameSessionDto> {

    private String id;
    private String gameName;
    private String imageUrl;
    private String startDateTimestamp;
    private int duration;
    private List<Player> playerList;
    private String winnerPlayerId;


    @Override
    public int compareTo(GameSessionDto dto) {
        return this.startDateTimestamp.compareTo(dto.getStartDateTimestamp());
    }

}

