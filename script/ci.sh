#!/bin/bash

set -e
cd "$(dirname "$0")/.."

function server_is_live {
  curl --silent -o /dev/null localhost:8080
}

function server_is_gone {
  ! server_is_live
}

function wait_until {
  n=0
  max=20
  until "$@"; do
    if [[ $(( n++ )) = $max ]]; then
      echo "TIMEOUT waiting for $@"
      return 1
    fi
    echo -n .
    sleep 1
  done
}

if server_is_live; then
  echo "Server is already running"
  exit 1
fi

for d in 0-spring-jpa 1-spring-without-jpa 2-no-spring-no-jpa
do (
    cd $d
    echo "--------------------------- doing $d --------------------------------"
    echo "" | script/create-local-databases.sh
    ./gradlew clean --console=plain
    script/run-locally.sh --logging.level.root=WARN &
    pid=$!
    wait_until server_is_live
    echo "$d: running"
    script/acceptance-test.sh
    kill $pid
    wait_until server_is_gone
    echo "$d: stopped"
) done
