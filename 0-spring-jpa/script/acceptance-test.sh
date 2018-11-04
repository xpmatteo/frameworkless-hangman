#!/bin/bash

set -e

function fail() {
  echo $*
  exit 1
}

function assert_equals {
  if [[ "$1" != "$2" ]]; then
    echo $3
    echo "Expected: $1"
    echo "Actual:   $2"
    exit 1
  fi
}

gameId=$(curl -# -d "word=foo" http://localhost:8080/hangman/game | jq --raw-output .gameId)

actual=$(curl -# http://localhost:8080/hangman/game/$gameId)
read expected <<EOF
{"gameId":"$gameId","hits":[],"misses":[],"state":"help","guessesRemaining":18,"word":"***"}
EOF
assert_equals "$expected" "$actual" "Get game status"

actual=$(curl -# -d "guess=o" http://localhost:8080/hangman/game/$gameId/guesses)
read expected <<EOF
{"gameId":"$gameId","hits":["o"],"misses":[],"state":"help","guessesRemaining":17,"word":"*oo"}
EOF
assert_equals "$expected" "$actual" "First guess"

actual=$(curl -# -d "guess=x" http://localhost:8080/hangman/game/$gameId/guesses)
read expected <<EOF
{"gameId":"$gameId","hits":["o"],"misses":["x"],"state":"help","guessesRemaining":16,"word":"*oo"}
EOF
assert_equals "$expected" "$actual" "Second guess (a miss)"

actual=$(curl -# -d "guess=f" http://localhost:8080/hangman/game/$gameId/guesses)
read expected <<EOF
{"gameId":"$gameId","hits":["f","o"],"misses":["x"],"state":"rescued","guessesRemaining":15,"word":"foo"}
EOF
assert_equals "$expected" "$actual" "Third and final guess"

echo OK
