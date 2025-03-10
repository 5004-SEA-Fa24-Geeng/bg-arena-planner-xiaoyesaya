package student;

/**
 * Filter Class is a helper class which provides utility methods for
 * filtering board games based on different attributes.
 */
public final class Filters {
    /**
     * Constructor for Filters.
     */
    private Filters() {

    }

    /**
     * Filters a board game based on the column, operation, and value.
     * @param game the board game to be filtered.
     * @param column the attribute of the game.
     * @param op the operation to be performed.
     * @param value the value to compare against.
     * @return if the game meets the filter condition, return true, otherwise, return false.
     */
    public static boolean filter(BoardGame game, GameData column,
                                 Operations op, String value) {
        switch (column) {
            case NAME:
                // filter the name
                return filterString(game.getName(), op, value);
            case MIN_PLAYERS:
                // filter the minimum players
                return filterNumber(game.getMinPlayers(), op, value);
            case MAX_PLAYERS:
                // filter the maximum players
                return filterNumber(game.getMaxPlayers(), op, value);
            case MAX_TIME:
                // filter the maximum play time
                return filterNumber(game.getMaxPlayTime(), op, value);
            case MIN_TIME:
                // filter the minimum play time
                return filterNumber(game.getMinPlayTime(), op, value);
            case DIFFICULTY:
                // filter the difficulty
                return filterDouble(game.getDifficulty(), op, value);
            case RANK:
                // filter the rank
                return filterNumber(game.getRank(), op, value);
            case RATING:
                // filter the rating
                return filterDouble(game.getRating(), op, value);
            case YEAR:
                // filter the published year
                return filterNumber(game.getYearPublished(), op, value);
            default:
                // for other columns, return false
                return false;
        }
    }

    /**
     * Filters a string attribute of a board game based on the specified operation.
     *
     * @param gameData the string attribute of the board game to be filtered.
     * @param op       the operation to be performed.
     * @param value    the value to compare against.
     * @return if the string attributes meets the filter condition, return true, otherwise, return false.
     */
    public static boolean filterString(String gameData, Operations op, String value) {
        if (gameData == null || value == null) {
            return false;
        }
        switch (op) {
            case EQUALS:
                return gameData.equalsIgnoreCase(value);
            case NOT_EQUALS:
                return !gameData.equalsIgnoreCase(value);
            case CONTAINS:
                return gameData.toLowerCase().contains(value.toLowerCase());
            case GREATER_THAN:
                return gameData.compareToIgnoreCase(value) > 0;
            case LESS_THAN:
                return gameData.compareToIgnoreCase(value) < 0;
            case GREATER_THAN_EQUALS:
                return gameData.compareToIgnoreCase(value) >= 0;
            case LESS_THAN_EQUALS:
                return gameData.compareToIgnoreCase(value) <= 0;
            default:
                return false;
        }
    }

    /**
     * Filters an integer attribute of a board game based on the specified operation.
     *
     * @param gameData the integer attribute of the board game to be filtered.
     * @param op       the operation to be performed.
     * @param value    the value to compare against.
     * @return if the integer attributes meets the filter condition, return true, otherwise, return false.
     */
    public static boolean filterNumber(int gameData, Operations op, String value) {
        try {
            int compareValue = Integer.parseInt(value);
            switch (op) {
                case EQUALS:
                    return gameData == compareValue;
                case NOT_EQUALS:
                    return gameData != compareValue;
                case GREATER_THAN:
                    return gameData > compareValue;
                case LESS_THAN:
                    return gameData < compareValue;
                case GREATER_THAN_EQUALS:
                    return gameData >= compareValue;
                case LESS_THAN_EQUALS:
                    return gameData <= compareValue;
                default:
                    return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Filters a double attribute of a board game based on the specified operation.
     *
     * @param gameData the double attribute of the board game to be filtered.
     * @param op       the operation to be performed.
     * @param value    the value to compare against.
     * @return if the double attributes meets the filter condition, return true, otherwise, return false.
     */
    public static boolean filterDouble(double gameData, Operations op, String value) {
        try {
            double compareValue = Double.parseDouble(value);

            switch (op) {
                case EQUALS:
                    return gameData == compareValue;
                case NOT_EQUALS:
                    return gameData != compareValue;
                case GREATER_THAN:
                    return gameData > compareValue;
                case LESS_THAN:
                    return gameData < compareValue;
                case GREATER_THAN_EQUALS:
                    return gameData >= compareValue;
                case LESS_THAN_EQUALS:
                    return gameData <= compareValue;
                default:
                    return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
