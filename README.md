[![Build Status](https://travis-ci.org/xpmatteo/frameworkless-hangman.svg?branch=master)](https://travis-ci.org/xpmatteo/frameworkless-hangman)

# About

This is a backend for the game of "hangman" (guess a word).

The goal is to show how to start with a typical Spring/JPA app and remove Spring and JPA, with the advantages of a much faster boot time, much faster tests, vastly reduced memory footprint, and an overall simpler application to learn and maintain.

If you know how to replace Spring/JPA features, then you're in a position to make an informed decision whether these frameworks are worth using for you or not.  If you don't know how to replace Spring and JPA with plain servlets and JDBC, then you don't have a choice.  My goal is to help you have a choice.

See more information about the [Frameworkless movement](https://github.com/frameworkless-movement/manifesto)



# Instructions for all versions of the app

Prerequisites:

 - Java 8 
 - gradle
 - mysql or mariadb
 
Useful for testing:

 - curl
 - jq

Run unit tests with

    ./gradlew test

Run with

    script/create-local-databases.sh
    script/run-locally.sh

Test with curl:

    curl -X POST localhost:8080/hangman/game | jq
    curl localhost:8080/hangman/game/<game id> | jq
    curl -d guess=x localhost:8080/hangman/game/<game id>/guesses | jq


