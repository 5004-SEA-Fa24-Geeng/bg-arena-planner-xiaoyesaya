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
        - rangeParseHelper(String str int max): int[]
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
        + checkMatchCondition() Boolan
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

1. Test 1..
2. Test 2..




## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.





## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 
