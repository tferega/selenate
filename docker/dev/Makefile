IMAGE_NAME="mentatlabs/selenate-dev"

## Build image from Dockerfile
build:
	docker build --rm -t $(IMAGE_NAME) . && pack.sh

## Run container with default command
run:
	docker-compose up
