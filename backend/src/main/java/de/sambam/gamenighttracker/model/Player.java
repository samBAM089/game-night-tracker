package de.sambam.gamenighttracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
    private String id;
    private String name;
    private String profilePic;
    private String gamesPlayed;
    private String mostPlayedGames;
    private String winRatio;
    private String totalPlayTime;
}
