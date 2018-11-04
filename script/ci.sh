#!/bin/bash

set -e
(cd 0-spring-jpa;         echo "" | script/create-local-databases.sh)
(cd 0-spring-jpa;         ./gradlew test --console plain)
(cd 1-spring-without-jpa; ./gradlew test --console plain)
(cd 2-no-spring-no-jpa;   ./gradlew test --console plain)

