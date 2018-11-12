#!/bin/bash

set -e
cd $(dirname $0)/..

if [ "$1" != "--no-gradle" ]; then
  ./gradlew shadowJar -x test
fi

exec java \
  -jar "build/libs/hangman-0.0.1-SNAPSHOT-all.jar" $*
