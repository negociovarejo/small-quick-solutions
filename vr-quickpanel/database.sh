#!/bin/sh
export PGHOST=localhost
export PGPORT=5432
export PGDATABSE=samuka
export PGPASSWORD=postgres
export PGUSERNAME=postgres

psql -U $PGUSERNAME -h $PGHOST -p $PGPORT -d $PGDATABSE -A -c "$1" --field-separator=";"