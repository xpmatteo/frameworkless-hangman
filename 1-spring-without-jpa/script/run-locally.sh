#!/bin/bash

set -e
cd $(dirname $0)/..

if [ "$1" != "--no-gradle" ]; then
  ./gradlew build -x test
fi

exec java \
  -XX:TieredStopAtLevel=1 -noverify \
  -jar "build/libs/hangman-0.0.1-SNAPSHOT.jar" $*
