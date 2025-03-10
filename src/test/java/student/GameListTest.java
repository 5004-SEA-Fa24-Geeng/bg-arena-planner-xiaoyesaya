package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class GameListTest {
    public Set<BoardGame> games;
    @TempDir
    Path tempDir;
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
    void getGameNames() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());
        List<String> gameNames = list.getGameNames();
        assertEquals("17 days", gameNames.get(0));
        assertEquals("Tucano", gameNames.get(7));

    }

    @Test
    void clear() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());
        assertEquals(8, list.count());
        list.clear();
        assertEquals(0, list.count());
    }

    @Test
    void count() {
        IGameList list = new GameList();
        assertEquals(0, list.count());

        list.addToList("all", games.stream());
        assertEquals(8, list.count());

        list.removeFromList("1");
        assertEquals(7, list.count());
    }
    @Test
    void saveGame() throws IOException {
        IGameList list = new GameList();
        list.addToList("all", games.stream());

        Path tempFile = tempDir.resolve("games.txt");
        list.saveGame(tempFile.toString());

        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(8, lines.size());
        assertTrue(lines.contains("Go Fish"));
        assertTrue(lines.contains("Monopoly"));
    }

    @Test
    void testAddSingleGameToListByIndex() {
        IGameList list1 = new GameList();
        List<BoardGame> sortedGames = new ArrayList<>(games);
        sortedGames.sort(Sort.getComparator(GameData.NAME, true));
        list1.addToList("1", sortedGames.stream());
        assertEquals(1, list1.count());
        assertEquals("17 days", list1.getGameNames().get(0));
    }

    @Test
    void testAddSingleGameToListByName() {
        IGameList list1 = new GameList();
        list1.addToList("Chess", games.stream());
        assertEquals(1, list1.count());
        assertEquals("Chess", list1.getGameNames().get(0));

    }

    @Test
    void testAddAllGameToList() {
        IGameList list1 = new GameList();
        list1.addToList("all", games.stream());
        assertEquals(8, list1.count());
    }

    @Test
    void testAddRangeOfGamesToList() {
        IGameList list1 = new GameList();
        List<BoardGame> sortedGames = new ArrayList<>(games);
        sortedGames.sort(Sort.getComparator(GameData.NAME, true));
        list1.addToList("1-3", sortedGames.stream());
        assertEquals(3, list1.count());
        assertTrue(list1.getGameNames().contains("17 days"));
        assertTrue(list1.getGameNames().contains("Chess"));
        assertTrue(list1.getGameNames().contains("Go"));
    }

    @Test
    void testAddInvalidIndexThrows() {
        IGameList list1 = new GameList();
        assertThrows(IllegalArgumentException.class, () -> list1.addToList("10", games.stream()));
        assertThrows(IllegalArgumentException.class, () -> list1.addToList("-1", games.stream()));
    }

    @Test
    void testAddValidIndexDoesNotThrow() {
        IGameList list1 = new GameList();
        assertDoesNotThrow(() -> list1.addToList("1", games.stream()));
        assertDoesNotThrow(() -> list1.addToList("2", games.stream()));

    }

    @Test
    void testAddInvalidRangeThrows() {
        IGameList list1 = new GameList();
        assertThrows(IllegalArgumentException.class, () -> list1.addToList("5-10", games.stream()));
        assertThrows(IllegalArgumentException.class, () -> list1.addToList("9-3", games.stream()));
    }

    @Test
    void testAddValidRangeDoesNotThrow() {
        IGameList list1 = new GameList();
        assertDoesNotThrow(() -> list1.addToList("1-1", games.stream()));
        assertDoesNotThrow(() -> list1.addToList("1-3", games.stream()));

    }

    @Test
    void testAddGameByNameNotExistThrows() {
        IGameList list = new GameList();
        assertThrows(IllegalArgumentException.class, () -> list.addToList("Gaia Project", games.stream()));
    }

    @Test
    void testRemoveSingleByIndex() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());

        list.removeFromList("1");

        assertEquals(7, list.count());
        assertFalse(list.getGameNames().contains("17 days"));
    }

    @Test
    void testRemoveGameByName() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());

        list.removeFromList("Chess");

        assertEquals(7, list.count());
        assertFalse(list.getGameNames().contains("Chess"));
    }

    @Test
    void testRemoveRange() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());

        list.removeFromList("1-3");

        assertEquals(5, list.count());
        assertFalse(list.getGameNames().contains("17 days"));
        assertFalse(list.getGameNames().contains("Chess"));
        assertFalse(list.getGameNames().contains("Go"));
    }

    @Test
    void testRemoveInvalidIndexThrows() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());

        assertThrows(IllegalArgumentException.class, () -> list.removeFromList("20"));
    }

    @Test
    void testRemoveValidIndexDoesNotThrow() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());
        assertDoesNotThrow(() -> list.removeFromList("1"));
        assertDoesNotThrow(() -> list.removeFromList("2"));

    }

    @Test
    void testRemoveInvalidRangeThrows() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());

        assertThrows(IllegalArgumentException.class, () -> list.removeFromList("5-10"));
    }

    @Test
    void testRemoveValidRangeDoesNotThrow() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());
        assertDoesNotThrow(() -> list.removeFromList("1-1"));
        assertDoesNotThrow(() -> list.removeFromList("3-5"));

    }

    @Test
    void testRemoveFromEmptyListThrows() {
        IGameList list = new GameList();
        assertThrows(IllegalArgumentException.class, () -> list.removeFromList("1"));
    }

    @Test
    void testRemoveGameByNameNotExistThrows() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());
        assertThrows(IllegalArgumentException.class, () -> list.removeFromList("Azul"));
    }

    @Test
    void testRemoveAll() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());

        list.removeFromList("all");

        assertEquals(0, list.count());
    }


}