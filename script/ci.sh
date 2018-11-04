#!/bin/bash

set -e

(cd 0-spring-jpa; ./gradlew test)
(cd 1-spring-without-jpa; ./gradlew test)
(cd 2-no-spring-no-jpa; ./gradlew test)

