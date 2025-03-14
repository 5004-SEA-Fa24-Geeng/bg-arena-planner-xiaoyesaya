# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram 

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

### Provided Code

Provide a class diagram for the provided code as you read through it.  For the classes you are adding, you will create them as a separate diagram, so for now, you can just point towards the interfaces for the provided code diagram.



### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces. 

```mermaid
classDiagram
    IPlanner <|.. Planner : implements
    IGameList <|.. GameList : implements
    BGArenaPlanner ..> ConsoleApp : uses
    BGArenaPlanner ..> GamesLoader : uses
    GamesLoader ..> BoardGame: creates
    BoardGame ..> GameData : uses
    Planner ..> GameData : uses
    Planner ..> SortHelper : uses
    Planner ..> FilterHelper : uses
    GameList ..> BoardGame : uses
    SortHelper ..> GameData : uses
    ConsoleApp ..> IGameList : uses
    ConsoleApp ..> ConsoleText : uses
    ConsoleApp ..> IPlanner : uses
    FilterHelper  ..> Operations : uses


    class IPlanner {
        <<interface>>
        + filter(String filter) Stream~BoardGame~
        + filter(String filter, GameData sortOn) Stream~BoardGame~
        + filter(String filter, GameData sortOn, boolean ascending) Stream~BoardGame~  
        + reset() void
    }
    class IGameList {
        <<interface>>
        + ADD_ALL() String = "all"
        + getGameNames() List~String~
        + clear() void
        + count() int
        + saveGame(String filename) void
        + addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException: void
        + removeFromList(String str) throws IllegalArgumentException: void
    }
    class GameList{
        - listOfGames: Set~String~
        + GameList()
        + getGameNames() List~String~
        + clear() void
        + count() int
        + saveGame(String filename) void
        + addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException: void
        + removeFromList(String str) throws IllegalArgumentException: void
    }
    class Planner{
        - allGames: Set~BoardGame~
        - filterHelper: FilterHelper
        - SortHelper: SortHelper
        - filters: List~String~
        - column: GameData
        - ascending: boolean
        + Planner(Set~BoardGame~ games)  
        + filter(filter: String) Stream~BoardGame~
        + filter(filter: String, sortOn: GameData) Stream~BoardGame~
        + filter(filter: String, sortOn: GameData, ascending: boolean) Stream~BoardGame~
        + reset() void
    }
    class BGArenaPlanner{
        - DEFAULT_COLLECTION: String
        - private BGArenaPlanner
        + main(args: String[]) void

    }
    class BoardGame{
        - name: String
        - id: int
        - minPlayers: int    
        - maxPlayers: int       
        - minPlayTime: int        
        - maxPlayTime: int     
        - TAX_RATE: double
        - difficulty: double      
        - rank: int       
        - averageRating: double          
        - yearPublished: int      
        + BoardGame(String name, int id, int minPlayers, int maxPlayers, int minPlayTime, int maxPlayTime, double difficulty, int rank, double averageRating, int yearPublished)
        + getName() String     
        + getId() int       
        + getMinPlayers() int             
        + getMaxPlayers() int   
        + getMinPlayTime() int
        + getMaxPlayTime() int          
        + getDifficulty() double
        + getRank() int
        + getRating() double
        + getYearPublished() int
        + toStringWithInfo(col: GameData) String
        + toString() String
        + equals(obj: Object) boolean
        + hashCode() int           
    }
    class ConsoleApp{
        - IN: Scanner
        - DEFAULT_FILENAME: String
        - RND: Random
        - gameList: IGameList
        - planner: IPlanner
        - current: Scanner
        + ConsoleApp(gameList: IGameList, planner: IPlanner)
        + start() void
        - randomNumber: void
        - processHelp: void
        - processFilter: void
        - printFilterStream: void
        - processListCommands: void
        - printCurrentList: void
        - nextCommand: ConsoleText
        - remainder: String
        - getInput: String
        - printOutput: String

    }
    class ConsoleText{
        <<enum>>
        WELCOME, HELP, INVALID, GOODBYE, PROMPT, NO_FILTER, NO_GAMES_LIST, FILTERED_CLEAR, LIST_HELP, FILTER_HELP,INVALID_LIST, EASTER_EGG, CMD_EASTER_EGG,CMD_EXIT, CMD_HELP, CMD_QUESTION, CMD_FILTER, CMD_LIST,CMD_SHOW, CMD_ADD, CMD_REMOVE, CMD_CLEAR, CMD_SAVE,CMD_OPTION_ALL, CMD_SORT_OPTION, CMD_SORT_OPTION_DIRECTION_ASC, CMD_SORT_OPTION_DIRECTION_DESC
        - CTEXT: Properties
        + toString() String
        + fromString(String str) ConsoleText
    }
    class GamesLoader{
        - DELIMITER: String
        - GamesLoader
        + loadGamesFile(String filename) Set~BoardGame~
        - toBoardGame: BoardGame
        - processHeader: Map~GameData, Integer~

    }
    class Operations{
        <<enum>>
        EQUALS("=="), NOT_EQUALS("!="), GREATER_THAN(">"), LESS_THAN("<"), GREATER_THAN_EQUALS(
            ">="), LESS_THAN_EQUALS("<="), CONTAINS("~=")
        - operator: String
        - Operations
        + getOperator() String
        + fromOperator(String operator) Operations
        + getOperatorFromStr(String str) Operations
    }
    class GameData {
        <<enum>>
        NAME("objectname"), ID("objectid"), RATING("average"), DIFFICULTY("avgweight"), RANK("rank"), MIN_PLAYERS("minplayers"), MAX_PLAYERS("maxplayers"), MIN_TIME("minplaytime"), MAX_TIME("maxplaytime"), YEAR("yearpublished")
        - columnName: String
        - GameData
        + getColumnName() String
        + fromColumnName(String columnName) GameData
        + fromString(String name) GameData
    }
    class FilterHelper {
        + FilterProcessor()
        + applyFilter(String filter) Stream~BoardGame~
        + applySingleFilter() Stream~BoardGame~
        + checkMatchCondition() Boolean
    }
    class SortHelper {
        + SortHelper()
        + applySort() Stream~BoardGame~
    }

```

## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

1. Test that the `GameList` Class properly returns the list of game names in ascending order ignoring case from `getGameNames()`
2. Test that the `GameList` Class properly remove all the game completely in the list from `clear()`
3. Test that the `GameList` Class properly return the number of games in the list from `count()`
4. Test that the `GameList` Class properly saves the game in the list to a file from `saveGame()`
5. Test that the `GameList` Class properly add a game or games to the list from `addToList()`
6. Test that the `GameList` Class properly remove a game or games from the list from `removeFromList()`
7. Test that the `Planner` Class properly filter and sort by name as default from `filter(String Filter)`
8. Test that the `Planner` Class properly filter and sort by specific column from `filter(String Filter, GameData sortOn)`
9. Test that the `Planner` Class properly filter and sort by specific column in ascending or descending order from `filter(String filter, GameData sortOn, boolean ascending)`
10. Test that the `Planner` Class properly reset the collection to have no filters applied from `reset()`
11. Test that the `FilterHelper` Class properly apply multiple filter conditions from `applyFilter(String filter)`
12. Test that the `FilterHelper` Class properly apply a single filter condition from `applySingleFilter()`
13. Test that the `FilterHelper` Class properly determine whether a game match the filtered condition from `checkMatchCondition()`
14. Test that the `SortHelper` Class properly sorts a stream of BoardGame objects based on a given GameData field and support ascending and descending order from `applySort()`

## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.

```mermaid
classDiagram
    IPlanner <|.. Planner : implements
    IGameList <|.. GameList : implements
    BGArenaPlanner ..> ConsoleApp : uses
    BGArenaPlanner ..> GamesLoader : uses
    GamesLoader ..> BoardGame: creates
    GamesLoader ..> GameData: uses
    BoardGame ..> GameData : uses
    Planner ..> Sort : uses
    Planner ..> Filters : uses
    GameList ..> BoardGame : uses
    Sort ..> GameData : uses
    Sort ..> BoardGame : uses
    ConsoleApp ..> IGameList : uses
    ConsoleApp ..> ConsoleText : uses
    ConsoleApp ..> IPlanner : uses
    Filters  ..> Operations : uses
    Filters  ..> GameData : uses
    Filters  ..> BoardGame : uses


    class IPlanner {
        <<interface>>
        + filter(String filter) Stream~BoardGame~
        + filter(String filter, GameData sortOn) Stream~BoardGame~
        + filter(String filter, GameData sortOn, boolean ascending) Stream~BoardGame~  
        + reset() void
    }
    class IGameList {
        <<interface>>
        + ADD_ALL() String = "all"
        + getGameNames() List~String~
        + clear() void
        + count() int
        + saveGame(String filename) void
        + addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException: void
        + removeFromList(String str) throws IllegalArgumentException: void
    }
    class GameList{
        - listOfGames: Set~String~
        + GameList()
        + getGameNames() List~String~
        + clear() void
        + count() int
        + saveGame(String filename) void
        + addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException: void
        + removeFromList(String str) throws IllegalArgumentException: void
        - rangeParseHelper(String str int max): int[]
    }
    class Planner{
        - allGames: Set~BoardGame~
        - filteredGame: Set~BoardGame~
        + Planner(Set~BoardGame~ games)  
        + filter(filter: String) Stream~BoardGame~
        + filter(filter: String, sortOn: GameData) Stream~BoardGame~
        + filter(filter: String, sortOn: GameData, ascending: boolean) Stream~BoardGame~
        + reset() void
    }
    class BGArenaPlanner{
        - DEFAULT_COLLECTION: String
        - private BGArenaPlanner
        + main(args: String[]) void

    }
    class BoardGame{
        - name: String
        - id: int
        - minPlayers: int    
        - maxPlayers: int       
        - minPlayTime: int        
        - maxPlayTime: int     
        - TAX_RATE: double
        - difficulty: double      
        - rank: int       
        - averageRating: double          
        - yearPublished: int      
        + BoardGame(String name, int id, int minPlayers, int maxPlayers, int minPlayTime, int maxPlayTime, double difficulty, int rank, double averageRating, int yearPublished)
        + getName() String     
        + getId() int       
        + getMinPlayers() int             
        + getMaxPlayers() int   
        + getMinPlayTime() int
        + getMaxPlayTime() int          
        + getDifficulty() double
        + getRank() int
        + getRating() double
        + getYearPublished() int
        + toStringWithInfo(col: GameData) String
        + toString() String
        + equals(obj: Object) boolean
        + hashCode() int           
    }
    class ConsoleApp{
        - IN: Scanner
        - DEFAULT_FILENAME: String
        - RND: Random
        - gameList: IGameList
        - planner: IPlanner
        - current: Scanner
        + ConsoleApp(gameList: IGameList, planner: IPlanner)
        + start() void
        - randomNumber: void
        - processHelp: void
        - processFilter: void
        - printFilterStream: void
        - processListCommands: void
        - printCurrentList: void
        - nextCommand: ConsoleText
        - remainder: String
        - getInput: String
        - printOutput: String

    }
    class ConsoleText{
        <<enum>>
        WELCOME, HELP, INVALID, GOODBYE, PROMPT, NO_FILTER, NO_GAMES_LIST, FILTERED_CLEAR, LIST_HELP, FILTER_HELP,INVALID_LIST, EASTER_EGG, CMD_EASTER_EGG,CMD_EXIT, CMD_HELP, CMD_QUESTION, CMD_FILTER, CMD_LIST,CMD_SHOW, CMD_ADD, CMD_REMOVE, CMD_CLEAR, CMD_SAVE,CMD_OPTION_ALL, CMD_SORT_OPTION, CMD_SORT_OPTION_DIRECTION_ASC, CMD_SORT_OPTION_DIRECTION_DESC
        - CTEXT: Properties
        + toString() String
        + fromString(String str) ConsoleText
    }
    class GamesLoader{
        - DELIMITER: String
        - GamesLoader
        + loadGamesFile(String filename) Set~BoardGame~
        - toBoardGame: BoardGame
        - processHeader: Map~GameData, Integer~

    }
    class Operations{
        EQUALS("=="), NOT_EQUALS("!="), GREATER_THAN(">"), LESS_THAN("<"), GREATER_THAN_EQUALS(
            ">="), LESS_THAN_EQUALS("<="), CONTAINS("~=")
        - operator: String
        - Operations
        + getOperator() String
        + fromOperator(String operator) Operations
        + getOperatorFromStr(String str) Operations
    }
    class GameData {
        NAME("objectname"), ID("objectid"), RATING("average"), DIFFICULTY("avgweight"), RANK("rank"), MIN_PLAYERS("minplayers"), MAX_PLAYERS("maxplayers"), MIN_TIME("minplaytime"), MAX_TIME("maxplaytime"), YEAR("yearpublished")
        - columnName: String
        - GameData
        + getColumnName() String
        + fromColumnName(String columnName) GameData
        + fromString(String name) GameData
    }
    class Filters {
        - Filters
        + filter(BoardGame game, GameData column, Operations op, String value) boolean
        + filterString(String gameData, Operations op, String value) boolean
        + filterNumber(int gameData, Operations op, String value) boolean
        + filterDouble(double gameData, Operations op, String value) boolean
    }
    class Sort {
        - Sort
        + getComparator(GameData sortOn, boolean ascending) Comparator~BoardGame~
    }


```

## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 

When first design the project, I am not sure about what the helper class should do (i.e. Filters and Sort). 
Now, I creates multiple helper methods in Filters to checks whether a game matches a filtering condition considering there are different data type such as string and int. I also creates comparators for sorting board games in Sort, which is a nice and flexible way when I need to sort using different ways. Moreover, I make the constructor of these two classes private as they are helper classes.
For Planner classes, I deleted the fields that I never used and added one field called filteredGame since I want my program can do progressive filtering. filteredGames starts as a copy of allGames, and when I apply a filter, it filters only filteredGames.