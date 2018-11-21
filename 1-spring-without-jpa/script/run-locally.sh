#!/bin/bash

set -e
cd $(dirname $0)/..

if [ "$1" != "--no-gradle" ]; then
  ./gradlew build -x test --console=plain
fi

exec java \
  -jar "build/libs/hangman1-0.0.1-SNAPSHOT.jar" $*
