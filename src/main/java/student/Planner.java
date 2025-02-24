package student;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class Planner implements IPlanner {
    private final Set<BoardGame> allGames;
    private Set<BoardGame> filteredGames;
    private List<String> filters;
    private GameData column;
    private Boolean ascending;


    public Planner(Set<BoardGame> games) {
        // TODO Auto-generated method stub
        this.allGames = games;
        this.filteredGames = new HashSet<>();
        this.filters = new ArrayList<>();
        this.column = GameData.NAME;
        this.ascending = true;

    }

    @Override
    public Stream<BoardGame> filter(String filter) {
        // TODO Auto-generated method stub
        return filter(filter, GameData.NAME, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        // TODO Auto-generated method stub
        return filter(filter, sortOn, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filter'");
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }


}
