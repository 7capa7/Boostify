.PHONY: build docker-build docker-run docker-stop start-dev-env stop-dev-env build-all-local

export APP_NAME ?= boostify
export IMAGE_NAME ?= ${APP_NAME}
export BASE_IMAGE ?= openjdk
export BASE_IMAGE_TAG ?= 17-jdk-slim-buster

build: ## Build project
	mvn clean package

docker-build: ## Build docker image
	docker build -t boostify \
	  --build-arg baseImage=openjdk \
	  --build-arg baseImageTag=17-jdk-slim-buster \
	  .

build-all-local: build docker-build

docker-run: ## Run docker container
	docker run -d --name=${APP_NAME} ${APP_NAME}

docker-stop: ## Stops docker container
	docker stop ${APP_NAME}

start-dev-env: ## Starts dev environment
	docker-compose up -d

stop-dev-env: ## Stops dev environment
	docker-compose down
