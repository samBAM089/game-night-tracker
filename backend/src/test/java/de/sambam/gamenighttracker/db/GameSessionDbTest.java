package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.GameSession;
import de.sambam.gamenighttracker.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


class GameSessionDbTest {

    @Test
    @DisplayName("addGameSession method should add new game session to db")
    public void addGameSessionTest() {
        //GIVEN
        GameSessionDb gameSessionDb = new GameSessionDb();
        GameSession gameSession = GameSession.builder().gameName("CloudAge").build();

        //WHEN
        gameSessionDb.addGameSession(gameSession);

        //THEN
        assertTrue(gameSessionDb.getGameSessionList().contains(GameSession.builder().gameName("CloudAge").build()));
    }

    @Test
    @DisplayName("getGameSessionList should return all sessions from Db")
    public void getGameSessionList() {
        //GIVEN
        GameSessionDb gameSessionDb = new GameSessionDb();

        Player player1 = Player.builder().name("samBAM").build();
        Player player2 = Player.builder().name("Tanja").build();
        Player player3 = Player.builder().name("Wolfi").build();
        Player player4 = Player.builder().name("Sarah").build();

        GameSession gameSession1 = GameSession.builder().gameName("CloudAge").playerList(List.of(player1, player2)).build();
        GameSession gameSession2 = GameSession.builder().gameName("Azul").playerList(List.of(player1, player3)).build();
        GameSession gameSession3 = GameSession.builder().gameName("Viticulture").playerList(List.of(player1, player4)).build();

        gameSessionDb.addGameSession(gameSession1);
        gameSessionDb.addGameSession(gameSession2);
        gameSessionDb.addGameSession(gameSession3);

        //WHEN
        List<GameSession> allTheSessions = gameSessionDb.getGameSessionList();
        List<GameSession> expectedList = List.of(
                GameSession.builder().gameName("CloudAge").playerList(List.of(player1, player2)).build(),
                GameSession.builder().gameName("Azul").playerList(List.of(player1, player3)).build(),
                GameSession.builder().gameName("Viticulture").playerList(List.of(player1, player4)).build()
        );

        //THEN
        assertTrue(allTheSessions.containsAll(expectedList));
    }
}