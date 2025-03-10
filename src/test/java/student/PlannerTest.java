package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlannerTest {
    static Set<BoardGame> games;
    Planner planner;
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
        planner = new Planner(games);
    }

    @Test
    void testFilter_EmptyFilter() {
        List<BoardGame> result = planner.filter("").toList();

        assertEquals(8, result.size());
        assertEquals("17 days", result.get(0).getName());
        assertEquals("Tucano", result.get(7).getName());
    }

    @Test
    void testFilter_NameEquals() {
        List<BoardGame> result = planner.filter("name==Monopoly").toList();

        assertEquals(1, result.size());
        assertEquals("Monopoly", result.get(0).getName());
    }

    @Test
    void testFilter_NameContains() {
        List<BoardGame> result = planner.filter("name~=Go").toList();

        assertEquals(4, result.size());
        assertEquals("Go", result.get(0).getName());
        assertEquals("Go Fish", result.get(1).getName());
        assertEquals("golang", result.get(2).getName());
        assertEquals("GoRami", result.get(3).getName());
    }

    @Test
    void testFilter_MinPlayersGreaterThanEquals() {
        List<BoardGame> result = planner.filter("minPlayers>=6").toList();
        assertEquals(3, result.size());
        assertEquals("GoRami", result.get(0).getName());
        assertEquals("Monopoly", result.get(1).getName());
        assertEquals("Tucano", result.get(2).getName());
    }

    @Test
    void testFilter_RatingGreaterThan8() {
        List<BoardGame> result = planner.filter("rating>8").toList();

        assertEquals(4, result.size());
        assertEquals("17 days", result.get(0).getName());
        assertEquals("Chess", result.get(1).getName());
        assertEquals("golang", result.get(2).getName());
        assertEquals("GoRami", result.get(3).getName());

    }

    @Test
    void testFilter_MinPlayersEqualsTen() {
        List<BoardGame> result = planner.filter("minplayers==10").toList();

        assertEquals(1, result.size());
        assertEquals("Tucano", result.get(0).getName());

    }

    @Test
    void testFilter_MultipleFilters() {
        List<BoardGame> result = planner.filter("rating>8,minplayers>=4").toList();

        assertEquals(1, result.size());
        assertEquals("GoRami", result.get(0).getName());
    }

    @Test
    void testNullFilter() {
        List<BoardGame> result = planner.filter(null).toList();
        assertEquals(0, result.size());
        assertEquals("17 days", result.get(0).getName());
    }

    @Test
    void testFilter_InvalidOperatorAndFormat() {
        List<BoardGame> result1 = planner.filter("rating=9").toList();
        assertEquals(8, result1.size());

        List<BoardGame> result2 = planner.filter("rating>>5").toList();
        assertEquals(8, result2.size());
    }
    @Test
    void reset() {
        planner.filter("rating>8");
        planner.reset();

        List<BoardGame> result = planner.filter("").toList();

        assertEquals(8, result.size());
        assertEquals("17 days", result.get(0).getName());
    }
}