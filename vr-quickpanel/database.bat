@echo off
SET PGHOST=localhost
SET PGPORT=5432
SET PGDATABSE=samuka
SET PGPASSWORD=postgres
SET PGUSERNAME=postgres

psql -U %PGUSERNAME% -h %PGHOST% -p %PGPORT% -d %PGDATABSE% -A -F ; -c %1