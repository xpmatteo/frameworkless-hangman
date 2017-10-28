#!/bin/bash

# define key information
dbname=unsprung
dbuser=unsprung
dbpassword=unsprung

# no customization needed beyond this line
set -e
cd "$(dirname "$0")/.."
if [ "$MYSQL_PWD" == "" ]; then
  read -s -p "mysql root password? (type return for no password) " MYSQL_PWD
  echo "Thanks. Please define the MYSQL_PWD env var to avoid this question."
  export MYSQL_PWD
fi

function do_admin() {
  mysqladmin -uroot $*
}

function do_mysql() {
  mysql -uroot $*
}

for db in $dbname ${dbname}_test
do
  do_admin --force drop $db || true
  do_admin create $db
  echo "$db created"

  echo "grant all on $db.* to '$dbuser'@localhost identified by '$dbpassword';" | do_mysql
  echo "$dbuser authorized"

  echo "Loading seed data"
  cat src/main/sql/*.sql | do_mysql $db
done
