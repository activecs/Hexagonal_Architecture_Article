#!/usr/bin/env bash
PREFIX="eder"
NAME="moviesfrontend"
# get build version from file package.json
VERSION=$(cat package.json | jq -r .version)
# build image
docker build -t "$PREFIX/$NAME:$VERSION" -t "$PREFIX/$NAME:latest" -t "localhost:5000/$NAME:latest" .
docker push "localhost:5000/$NAME:latest"