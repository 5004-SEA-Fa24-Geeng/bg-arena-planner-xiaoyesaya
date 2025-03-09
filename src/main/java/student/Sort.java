package student;

import java.util.Comparator;

/**
 *  Sort Class is a helper class which provides a comparator for
 *  sorting board games based on different attributes.
 */
public final class Sort {

    /**
     * Constructor for Sort
     */
    private Sort() {}

    /**
     * Returns a comparator for sorting BoardGame objects based on the given attribute.
     *
     * @param sortOn   The attribute to sort on.
     * @param ascending Whether to sort in ascending order.
     * @return A comparator for sorting BoardGame objects.
     */
    public static Comparator<BoardGame> getComparator(GameData sortOn, boolean ascending) {
        Comparator<BoardGame> comparator;

        switch (sortOn) {
            case NAME:
                comparator = Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case ID:
                comparator = Comparator.comparingInt(BoardGame::getId);
                break;
            case MIN_PLAYERS:
                comparator = Comparator.comparingInt(BoardGame::getMinPlayers);
                break;
            case MAX_PLAYERS:
                comparator = Comparator.comparingInt(BoardGame::getMaxPlayers);
                break;
            case MIN_TIME:
                comparator = Comparator.comparingInt(BoardGame::getMinPlayTime);
                break;
            case MAX_TIME:
                comparator = Comparator.comparingInt(BoardGame::getMaxPlayTime);
                break;
            case DIFFICULTY:
                comparator = Comparator.comparingDouble(BoardGame::getDifficulty);
                break;
            case RATING:
                comparator = Comparator.comparingDouble(BoardGame::getRating);
                break;
            case YEAR:
                comparator = Comparator.comparingInt(BoardGame::getYearPublished);
                break;
            default:
                comparator = Comparator.comparing(BoardGame::getName);
        }

        return ascending ? comparator : comparator.reversed();
    }
}
