package de.sambam.gamenighttracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {
    private String id;
    private String apiGameId;
    private String name;
    private String releaseYear;
    private String thumbnailUrl;
    private List<GameSession> gameSessionList;
}
