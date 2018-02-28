run:
	heroku local

repl:
	lein repl

initdb:
	psql < dbinit.sql