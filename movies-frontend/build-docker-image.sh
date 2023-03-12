#!/usr/bin/env bash
PREFIX="eder"
NAME="moviesfrontend"
VERSION=$(cat package.json | jq -r .version)

minikube image build -t "$PREFIX/$NAME:$VERSION" -t "$PREFIX/$NAME:latest" .

echo "Upload to minikube is done for -> $PREFIX/$NAME:latest"