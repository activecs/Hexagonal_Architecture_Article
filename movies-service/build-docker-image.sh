#!/usr/bin/env bash
PREFIX="eder"
NAME="moviesservice"
VERSION=$(grep -oPm1 "(?<=<version>)[^<]+" pom.xml)

minikube image build -t "$PREFIX/$NAME:$VERSION" -t "$PREFIX/$NAME:latest" .

echo "Upload to minikube is done for -> $PREFIX/$NAME:latest"