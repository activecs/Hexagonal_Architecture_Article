#!/usr/bin/env bash

#kubectl apply -f 1-jaeger.yaml

kubectl delete -f 5-deploy-movies-service.yaml
kubectl delete -f 6-deploy-movies-bff.yaml
kubectl delete -f 7-deploy-movies-frontend.yaml

sleep 5

(echo "build movies-service" && cd ../movies-service && /bin/bash build-docker-image.sh ) &
(echo "build movies-bff" && cd ../movies-bff && /bin/bash build-docker-image.sh ) &
(echo "build movies-frontend" && cd ../movies-frontend && /bin/bash build-docker-image.sh) &
wait

kubectl apply -f 5-deploy-movies-service.yaml
kubectl apply -f 6-deploy-movies-bff.yaml
kubectl apply -f 7-deploy-movies-frontend.yaml

echo "################## All done ##################"