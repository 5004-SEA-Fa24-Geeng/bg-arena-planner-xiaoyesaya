package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FiltersTest {
    public Set<BoardGame> games;

    @BeforeEach
    void setUp() {
        games = new HashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));
    }

    @Test
    void filter() {
        BoardGame game = games.stream()
                .filter(g -> g.getName().equalsIgnoreCase("Chess"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Game not found: Chess"));
        assertTrue(Filters.filter(game, GameData.NAME, Operations.EQUALS, "Chess"));
        assertTrue(Filters.filter(game, GameData.MIN_PLAYERS, Operations.GREATER_THAN, "1"));
        assertFalse(Filters.filter(game, GameData.MIN_PLAYERS, Operations.LESS_THAN, "1"));
        assertTrue(Filters.filter(game, GameData.MAX_PLAYERS, Operations.LESS_THAN, "3"));
        assertTrue(Filters.filter(game, GameData.DIFFICULTY, Operations.EQUALS, "10.0"));
        assertTrue(Filters.filter(game, GameData.MIN_TIME, Operations.GREATER_THAN, "5"));
        assertTrue(Filters.filter(game, GameData.MAX_TIME, Operations.LESS_THAN, "30"));
        assertTrue(Filters.filter(game, GameData.RANK, Operations.EQUALS, "700"));
        assertTrue(Filters.filter(game, GameData.RATING, Operations.EQUALS, "10.0"));
        assertTrue(Filters.filter(game, GameData.YEAR, Operations.LESS_THAN, "2010"));

    }

    @Test
    void filterString() {
        String name = "Chess";
        assertTrue(Filters.filterString(name, Operations.EQUALS, "Chess"));
        assertTrue(Filters.filterString(name, Operations.EQUALS, "chess"));
        assertFalse(Filters.filterString(name, Operations.EQUALS, "Go"));
        assertTrue(Filters.filterString(name, Operations.NOT_EQUALS, "Go"));
        assertFalse(Filters.filterString(name, Operations.NOT_EQUALS, "Chess"));
        assertTrue(Filters.filterString(name, Operations.CONTAINS, "ch"));
        assertFalse(Filters.filterString(name, Operations.CONTAINS, "cat"));
        assertFalse(Filters.filterString(null, Operations.EQUALS, null));

    }

    @Test
    void filterNumber() {
        int minPlayers = 2;
        assertTrue(Filters.filterNumber(minPlayers, Operations.EQUALS, "2"));
        assertFalse(Filters.filterNumber(minPlayers, Operations.EQUALS, "3"));
        assertTrue(Filters.filterNumber(minPlayers, Operations.GREATER_THAN, "1"));
        assertFalse(Filters.filterNumber(minPlayers, Operations.GREATER_THAN, "3"));
        assertTrue(Filters.filterNumber(minPlayers, Operations.LESS_THAN_EQUALS, "2"));
        assertFalse(Filters.filterNumber(minPlayers, Operations.LESS_THAN_EQUALS, "1"));
        assertFalse(Filters.filterNumber(minPlayers, Operations.EQUALS, "abc"));
    }



    @Test
    void filterDouble() {
        double rating = 7.5;
        assertTrue(Filters.filterDouble(rating, Operations.EQUALS, "7.5"));
        assertFalse(Filters.filterDouble(rating, Operations.EQUALS, "9.0"));
        assertTrue(Filters.filterDouble(rating, Operations.GREATER_THAN, "7.0"));
        assertFalse(Filters.filterDouble(rating, Operations.GREATER_THAN, "8.0"));
        assertTrue(Filters.filterDouble(rating, Operations.LESS_THAN_EQUALS, "7.5"));
        assertFalse(Filters.filterDouble(rating, Operations.LESS_THAN_EQUALS, "7.0"));
        assertFalse(Filters.filterDouble(rating, Operations.EQUALS, "aaa"));
    }
}