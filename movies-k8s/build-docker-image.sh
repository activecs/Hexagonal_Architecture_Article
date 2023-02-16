#!/usr/bin/env bash

(echo "build movies-service" && cd ../movies-service && /bin/bash build-docker-image.sh ) &
(echo "build movies-bff" && cd ../movies-bff && /bin/bash build-docker-image.sh ) &
(echo "build movies-frontend" && cd ../movies-frontend && /bin/bash build-docker-image.sh) &
wait
echo "All done"