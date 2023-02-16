#!/usr/bin/env bash
PREFIX="eder"
NAME="moviesservice"
# get build version from file pom.xml
VERSION=$(grep -oPm1 "(?<=<version>)[^<]+" pom.xml)
docker build -t "$PREFIX/$NAME:$VERSION" -t "$PREFIX/$NAME:latest" .
minikube image load "$PREFIX/$NAME:latest"
echo "Upload to minikube is done for -> $PREFIX/$NAME:latest"