package de.sambam.gamenighttracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {
    private String id;
    private String name;
    private String releaseYear;
    private String boxArt;
    private String lastPlay;
    private String mostWins;
    private String highScore;
    private String averageScore;
    private String totalPlayTime;
}
