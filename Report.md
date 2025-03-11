# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.
   
== compares object references, which is the memory address, while .equals compares object contents if it is overridden in the class.
   ```java
   // your code here
   String a = new String("Hello");
   String b = new String("Hello");
   System.out.println(a == b); // return false
   System.out.println(a.equals(b)); //return true if overridden, else false
   ```




2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner? 

I can sort a list of strings in a case-insensitive way using String.CASE_INSENSITIVE_ORDER. For example in this program, I used in public List<String> getGameNames to show a current list of games sorted alphabetically and ignoring the case.



3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point. 

The order matters because some operators (like >=) include >. If we check the shorter ones (i.e. >) first, they will match too early and give the wrong result.

4. What is the difference between a List and a Set in Java? When would you use one over the other? 

A List in Java allows duplicate elements and maintains the order of the items you add. A Set does not allow duplicates and does not guarantee order.
I will use a List when I want to keep things in order or allow repeats. For example, I use a list of strings for sortedGames since they need to be in an order. On the other hand, I used set when I want to make sure there are no duplicates. For example, I use set of string to represent the names of board games currently in the game list.

5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here? 

A map represents a mapping between a key and a value, which is in java.util package.
In GamesLoader.java, a Map<GameData, Integer> is used in the processHeader method to associate each game data field with its corresponding column index in the CSV file. This makes it easy to look up which column contains which data,


6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?

An enum is a class which represent a group of constants.
In GameData.java, GameData is an enum that represents different column names from the CSV file, such as NAME, RATING, and MIN_PLAYERS.
Instead of relying on strings which can lead to typos, the enum enforces correctness at compile time. I will not use a column name that does not exist.





7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
    // your code here, don't forget the class name that is dropped in the switch block..
   
   if (ct == CMD_QUESTION || ct == CMD_HELP) {
    processHelp();
   } else if (ct == INVALID) {
   CONSOLE.printf("%s%n", ConsoleText.INVALID);
   } else {
   CONSOLE.printf("%s%n", ConsoleText.INVALID);
   }
    
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
*******Bienvenido a la BoardGame Arena Planner.*******

Una herramienta para ayudar a las personas a planificar los juegos que
quieren jugar en Board Game Arena.

Para comenzar, ingrese su primer comando a continuacion, o escriba ? o ayuda para ver las opciones del comando.
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 


As a previous student learning Japanese pedagogy, I learned how mutual understanding across culture was very important and difficult to acheive. An inappropriate translation will lead to unpleasant feeling to people in the target culture. For example, if translating "Welcome" in English to "irasshaimase" in Japanese in the airport will be strange as this is not a commercial setting, which could be offensive to Japanese people [^1].
I think it is the same when comes to localization, where a good localization will improve the impression of customers since they feel they are valued and their cultures are valued. A bad localization may reduce the global sales and love of customers [^2]. One famous bad localization is the KFC's translation of slogan "Finger-lickin' good" to "eat your fingers off." in Chinese ads, which may prevent customers from going to the restauarants. 
However, to have good localization, it is more than translation. There are more things to be considered about such as the format of the date and address, the currency in different countries, and text adjusting for different languages [^3]. For example, the format of date in China usually start from date and end with year, which is different from US. It could cause confusion when using the US convention to write the date.

## References

[^1]: The pedagogy of performing another culture. Walker, Galal. https://openlibrary.org/works/OL15987920W/The_pedagogy_of_performing_another_culture. Accessed: 2025-03-10.
[^2]: What is Software Localization? https://www.lionbridge.com/blog/content-transformation/what-is-software-localization-and-who-needs-it/. Accessed: 2025-03-10.
[^3]: Is localisation still needed in modern softwate https://www.reddit.com/r/ExperiencedDevs/comments/17a86fq/is_localisation_still_needed_in_modern_softwate/ Accessed: 2025-03-10.