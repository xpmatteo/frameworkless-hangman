#!/bin/bash

set -e
cd $(dirname $0)/..

./gradlew build -x test

exec java \
  -XX:TieredStopAtLevel=1 -noverify \
  -jar "build/libs/frameworkless-hangout-0.0.1-SNAPSHOT.jar" $*
