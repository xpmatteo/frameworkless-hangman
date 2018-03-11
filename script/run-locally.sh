#!/bin/bash

set -e
cd $(dirname $0)/..

./gradlew build -x test

exec java -jar "build/libs/frameworkless-hangout-0.0.1-SNAPSHOT.jar" $*
