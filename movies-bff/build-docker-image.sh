#!/usr/bin/env bash
PREFIX="eder"
NAME="moviesbff"
# get build version from file pom.xml
VERSION=$(grep -oPm1 "(?<=<version>)[^<]+" pom.xml)
docker build -t "$PREFIX/$NAME:$VERSION" -t "$PREFIX/$NAME:latest" -t "$(minikube ip):5000/$NAME:latest" .
minikube image load "$(minikube ip):5000/$NAME:latest"