.PHONY: build
build:
	@mvn package

.PHONY: run
run: build
	@mvn exec:java