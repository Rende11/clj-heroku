run:
	heroku local

repl:
	lein repl

test:
	lein test

initdb:
	psql < dbinit.sql

dropdb:
	psql < dbdrop.sql

