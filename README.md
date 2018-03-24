This repo implements the game of "hangman" (guess a word).

The goal is to show how to start with a typical Spring/JPA app and remove Spring and JPA, with the advantages of a much faster boot time, much faster tests, vastly reduced memory footprint, and an overall simpler application to learn and maintain.

If you know how to replace Spring/JPA features, then you're in a position to make an informed decision whether these frameworks are worth using for you or not.  If you don't know how to replace Spring and JPA with plain servlets and JDBC, then you don't have a choice.  My goal is to help you have a choice.


Prerequisites

    Java 8, gradle, mysql or mariadb

Run with

    script/create-local-databases.sh
    gradle build
    java -jar ./build/libs/hangman-0.0.1-SNAPSHOT.jar

Test with

    curl -X POST localhost:8080/hangman/game | jq
    curl localhost:8080/hangman/game/1465d2ab57dec5be | jq
    curl -d guess=x localhost:8080/hangman/game/48d0fa5b3ae8ac40/guesses | jq

