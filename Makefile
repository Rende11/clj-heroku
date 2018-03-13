run:
	heroku local

repl:
	lein repl

test:
	lein test

initdb-local:
	psql < dbinit.sql

dropdb-local:
	psql < dbdrop.sql

initdb-heroku:
	heroku pg:psql < dbinit.sql

dropdb-heroku:
	heroku pg:psql < dbdrop.sql


.PHONY: test
