
Run with

	script/create-local-databases.sh
	gradle build
	java -jar ./build/libs/unsprung-0.0.1-SNAPSHOT.jar

Test with

	curl -X POST localhost:8080/hangman/game | jq
	curl localhost:8080/hangman/game/1465d2ab57dec5be | jq
	curl -d guess=x localhost:8080/hangman/game/48d0fa5b3ae8ac40/guesses | jq

