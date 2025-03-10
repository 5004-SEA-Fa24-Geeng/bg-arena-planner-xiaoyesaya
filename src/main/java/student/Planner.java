package student;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Planner class implements the IPlanner interface and manages filtering and sorting
 * operations on a collection of board games.
 *
 * It allows progressive filtering of board games based on different criteria, and maintains
 * a filtered set of games that can be reset at any time.
 */
public class Planner implements IPlanner {
    /** The complete collection of board games. */
    private final Set<BoardGame> allGames;
    /** The subset of board games after filters are applied. */
    private Set<BoardGame> filteredGames;

    /**
     * Constructor for the Planner.
     * @param games The complete collection of board games.
     */
    public Planner(Set<BoardGame> games) {
        this.allGames = games;
        this.filteredGames = new HashSet<>(games);

    }

    /**
     * Filters the board games using the provided filter string.
     *
     * Results are sorted in ascending order by the name of the board game (GameData.NAME).
     *
     * @param filter The filter string to apply to the board games.
     * @return A stream of board games matching the filter conditions.
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        return filter(filter, GameData.NAME, true);
    }

    /**
     * Filters the board games using the provided filter string and sorts the results on
     * the specified column in ascending order.
     *
     * @param filter The filter string to apply to the board games.
     * @param sortOn The column to sort the results on.
     * @return A stream of board games matching the filter conditions, sorted on the specified column.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    /**
     * Filters the board games using the provided filter string and sorts the results
     * on the specified column in the specified direction.
     * @param filter The filter to apply to the board games.
     * @param sortOn The column to sort the results on.
     * @param ascending Whether to sort the results in ascending order or descending order.
     * @return A stream of board games matching the filter conditions and sorted as specified.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        if (filter == null || filter.isEmpty()) {
            return filteredGames.stream().sorted(Sort.getComparator(sortOn, ascending));
        }
        Stream<BoardGame> stream = filteredGames.stream();

        String[] conditions = filter.split(",");
        for (String condition : conditions) {
            condition = condition.trim();
            Operations op = Operations.getOperatorFromStr(condition);
            if (op == null) {
                continue;
            }
            String[] parts = condition.split(op.getOperator());
            if (parts.length != 2) {
                continue;
            }
            GameData column = GameData.fromString(parts[0].trim());
            String value = parts[1].trim();
            stream = stream.filter(game -> Filters.filter(game, column, op, value));
        }
        this.filteredGames = stream.sorted(Sort.getComparator(sortOn, ascending))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return this.filteredGames.stream();
    }

    /**
     * Resets the collection of filtered games back to the complete collection of games.
     *
     * All applied filters are cleared.
     */
    @Override
    public void reset() {
        this.filteredGames = new HashSet<>(this.allGames);
    }


}
