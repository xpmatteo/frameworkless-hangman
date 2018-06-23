#!/bin/bash

echo "" | ./script/create-local-databases.sh
./gradlew build --console plain
