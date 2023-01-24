#!/usr/bin/env bash

(cd ../movies-service && /bin/bash build-docker-image.sh)
(cd ../movies-bff && /bin/bash build-docker-image.sh)
(cd ../movies-frontend && /bin/bash build-docker-image.sh)
