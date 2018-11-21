#!/bin/bash

set -e
cd $(dirname $0)/..

if [ "$1" != "--no-gradle" ]; then
  ./gradlew shadowJar -x test --console=plain
fi

exec java \
  -jar "build/libs/hangman2-0.0.1-SNAPSHOT-all.jar" $*
