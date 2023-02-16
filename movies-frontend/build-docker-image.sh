#!/usr/bin/env bash
PREFIX="eder"
NAME="moviesfrontend"
# get build version from file package.json
VERSION=$(cat package.json | jq -r .version)
docker build -t "$PREFIX/$NAME:$VERSION" -t "$PREFIX/$NAME:latest" .
minikube image load "$PREFIX/$NAME:latest"
echo "Upload to minikube is done for -> $PREFIX/$NAME:latest"