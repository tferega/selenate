IMAGE_NAME="mentatlabs/selenate"
TAG="0.3.8"

## Build image from Dockerfile
build:
	docker build --rm -t $(IMAGE_NAME) .

## Package service
pack:
	pack.sh

## Build release
release: | pack build

## Push image to hub
push:
	docker push $(IMAGE_NAME)

## Push tagged image to hub
push-tag:
	docker tag -f $(IMAGE_NAME) $(IMAGE_NAME):$(TAG) \
	&& docker push $(IMAGE_NAME):$(TAG)

## Run container with default command
run: 
	docker-compose up -d
