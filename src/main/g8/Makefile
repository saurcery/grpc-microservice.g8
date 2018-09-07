NAME=$(shell basename $(CURDIR) | tr A-Z a-z)
UNAME=$(shell basename $(CURDIR) | tr a-z A-Z)
PKG=github.com/zenreach/nwservices/$(NAME)
VERSION ?= $(shell git describe --always --dirty)
BRANCH ?= $(shell git rev-parse --abbrev-ref HEAD)
REPO := 577004102682.dkr.ecr.us-west-2.amazonaws.com

all: clean build test install

build:
	sbt compile

#test:

.PHONY: clean build test

docker:
	sbt docker
	docker build --no-cache -t nw$(NAME) $(CURDIR)/target/docker

push:
	docker tag nw$(NAME) $(REPO)/nw$(NAME):$(VERSION)
	docker push $(REPO)/nw$(NAME):$(VERSION)
ifneq ($(filter ${BRANCH},dev stag prod),)
	docker tag $(REPO)/nw$(NAME):$(VERSION) $(REPO)/nw$(NAME):$(BRANCH)
	docker push $(REPO)/nw$(NAME):$(BRANCH)
endif
