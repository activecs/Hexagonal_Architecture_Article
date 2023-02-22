#!/usr/bin/env bash
PREFIX="eder"
NAME="moviesservice"
VERSION=$(grep -oPm1 "(?<=<version>)[^<]+" pom.xml)

docker rmi "$PREFIX/$NAME:$VERSION"
docker rmi "$PREFIX/$NAME:latest"
docker build -t "$PREFIX/$NAME:$VERSION" -t "$PREFIX/$NAME:latest" .

minikube image rm  "$PREFIX/$NAME:latest"
minikube image load "$PREFIX/$NAME:latest"

echo "Upload to minikube is done for -> $PREFIX/$NAME:latest"