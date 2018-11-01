Prerequisites:

 - Java 8 
 - gradle
 - mysql or mariadb
 
Useful for testing:

 - curl
 - jq

Run with

    script/create-local-databases.sh
    gradle build
    java -jar build/libs/hangman-0.0.1-SNAPSHOT.jar

Test with

    curl -X POST localhost:8080/hangman/game | jq
    curl localhost:8080/hangman/game/<game id> | jq
    curl -d guess=x localhost:8080/hangman/game/<game id>/guesses | jq

