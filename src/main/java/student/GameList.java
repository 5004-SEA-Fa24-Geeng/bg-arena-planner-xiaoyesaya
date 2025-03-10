package student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a list of board games.
 *
 * Implements the IGameList interface to manage adding, removing, and retrieving
 * board game names as well as saving the list to a file.
 */
public class GameList implements IGameList {
    /** A set that stores the names of board games currently in the game list. */
    private final Set<String> listOfGames;
    /**
     * Constructor for the GameList.
     */
    public GameList() {
        listOfGames = new HashSet<>();
    }

    /**
     * Returns a sorted list of board game names currently in the list.
     *
     * @return A list of board game names sorted alphabetically (case-insensitive).
     */
    @Override
    public List<String> getGameNames() {
        return listOfGames.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }

    /**
     * Clears all board games from the current list.
     */
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        listOfGames.clear();
    }

    /**
     * Returns the number of board games in the list.
     *
     * @return The number of board games in the list.
     */
    @Override
    public int count() {
        // TODO Auto-generated method stub
        return listOfGames.size();
    }

    /**
     * Saves the list of board game names to a file.
     *
     * Each game name is written on a separate line in the file. If an error occurs
     * during writing, it prints an error message and stack trace.
     *
     * @param filename The name of the file to save the game names to.
     */
    @Override
    public void saveGame(String filename) {
        // TODO Auto-generated method stub
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (String game : getGameNames()) {
                writer.write(game);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Add a game or multiple games to the list.
     * @param str      the string to parse and add games to the list.
     * @param filtered the filtered list to use as a basis for adding.
     * @throws IllegalArgumentException If the input format is invalid, or if indices are out of bounds,
     * or if a specified game cannot be found.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        List<BoardGame> filteredList = filtered.toList();
        if (str.equalsIgnoreCase(ADD_ALL)) {
            for (BoardGame game : filteredList) {
                listOfGames.add(game.getName());
            }
            return;
        }
        try {
            if (str.matches("\\d+")) {
                int index = Integer.parseInt(str) - 1;
                if (index < 0 || index >= filteredList.size()) {
                    throw new IllegalArgumentException("Index out of bounds");
                }
                listOfGames.add(filteredList.get(index).getName());
                return;
            }
            if (str.matches("\\d+-\\d+")) {
                String[] parts = str.split("-");
                int start = Integer.parseInt(parts[0]) - 1;
                int end = Integer.parseInt(parts[1]) - 1;
                if (start < 0 || end >= filteredList.size() || start > end) {
                    throw new IllegalArgumentException("Invalid range: " + str);
                }
                for (int i = start; i <= end; i++) {
                    listOfGames.add(filteredList.get(i).getName());
                }
                return;
            }
            Optional<BoardGame> matchingGame = filteredList.stream()
                    .filter(game -> game.getName().equalsIgnoreCase(str))
                    .findFirst();
            if (matchingGame.isPresent()) {
                listOfGames.add(matchingGame.get().getName());
            } else {
                throw new IllegalArgumentException("Invalid game: " + str);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format: " + str);
        }
    }

    /**
     * Remove a game or multiple games from the list.
     * @param str The string to parse and remove games from the list.
     * @throws IllegalArgumentException If the input format is invalid, if indices are out of bounds,
     * if the list is empty, or if a specified game cannot be found.
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        if (str.equalsIgnoreCase(ADD_ALL)) {
            clear();
            return;
        }
        if (listOfGames.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        List<String> sortedGames = getGameNames();
        try {
            if (str.matches("\\d+")) {
                int index = Integer.parseInt(str) - 1;
                if (index < 0 || index >= sortedGames.size()) {
                    throw new IllegalArgumentException("Index out of bounds");
                }
                listOfGames.remove(sortedGames.get(index));
                return;
            }
            if (str.matches("\\d+-\\d+")) {
                String[] parts = str.split("-");
                int start = Integer.parseInt(parts[0]) - 1;
                int end = Integer.parseInt(parts[1]) - 1;
                if (start < 0 || end >= sortedGames.size() || start > end) {
                    throw new IllegalArgumentException("Invalid range: " + str);
                }
                Set<String> toRemove = new HashSet<>(sortedGames.subList(start, end + 1));
                listOfGames.removeAll(toRemove);
                return;
            }
            boolean removed = listOfGames.removeIf(game -> game.equalsIgnoreCase(str));
            if (!removed) {
                throw new IllegalArgumentException("Invalid game: " + str);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format: " + str);
        }
    }

}
