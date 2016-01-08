IMAGE_NAME="mentatlabs/selenate"
TAG="0.3.7"

## Build image from Dockerfile
build:
	pack.sh && docker build --rm -t $(IMAGE_NAME) .

## Push image to hub
push:
	docker push $(IMAGE_NAME)

## Push tagged image to hub
push-tag:
	docker push $(IMAGE_NAME):$(TAG)

## Run container with default command
run:
	docker-compose up
