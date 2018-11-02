#!/bin/bash

echo "" | ./script/create-local-databases.sh
./gradlew clean build --console plain
