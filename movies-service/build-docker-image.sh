#!/usr/bin/env bash
PREFIX="eder"
NAME="moviesservice"
# get build version from file pom.xml
VERSION=$(grep -oPm1 "(?<=<version>)[^<]+" pom.xml)
docker build -t "$PREFIX/$NAME:$VERSION" -t "$PREFIX/$NAME:latest" -t "localhost:5000/$NAME:latest" .
docker push "localhost:5000/$NAME:latest"