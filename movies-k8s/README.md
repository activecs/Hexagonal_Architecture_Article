# Local Deployment

## 1.1 Prerequisites: Install minikube with dashboard
```bash
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

minikube start

minikube addons enable dashboard
minikube addons enable metrics-server
minikube addons enable ingress

#minikube ip
#minikube addons list
#minikube dashboard --url
#watch minikube ssh -- cat /etc/hosts 
#kubectl port-forward --namespace kube-system service/registry 5000:80 
#kubectl port-forward --namespace default service/moviesfrontend 8050:8050
#kubectl port-forward --namespace default service/moviesservice 8888:80
```
## 1.2 Prerequisites: Install HELM
```bash
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh
```
## 1.3 Update hosts
```bash
echo "$(minikube ip) moviesfrontend" | sudo tee -a /etc/hosts
```

# 2 Deployment
## 2.1 Create and deploy Docker images
```bash
./build-docker-image.sh
```
OR
```bash
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
```

## 2.3 Deploy Prometheus + Grafana
```bash
# Add the prometheus-community and graphana Helm repository
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

# Install prometheus
helm install prometheus prometheus-community/prometheus
# expose prometheus-service:9090 via NodePort
kubectl expose service prometheus-server --type=NodePort --target-port=9090 --name=prometheus-server-np
minikube service prometheus-server-np

# Install grafana
helm install grafana grafana/grafana
# get admin password
kubectl get secret --namespace default grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo
# expose grafana-service:3000 via NodePort
kubectl expose service grafana --type=NodePort --target-port=3000 --name=grafana-np
minikube service grafana-np
```
Manual steps: (https://brain2life.hashnode.dev/prometheus-and-grafana-setup-in-minikube)
```bash
Create datasource from prometheus-server:8000
Import 6417 community Dashboard
```

# 4.1 Access the application
```bash
# http://moviesfrontend
```


# TODO:
- connect and collect metrics moviesservices, moviesbff in prometheus
- collect logs from moviesservices, moviesbff (lokki?)
- collect traces from moviesservices, moviesbff (jaeger?)
- add ingress for prometheus, grafana
- complete own helm chart
- add new service with own DB
- extend bff

# Automated Deployment (WIP)
# 3.1 Deploy to K8s using HELM
```bash
helm install moviesservice ./helm-charts/movies-service --namespace local --atomic
# helm upgrade moviesservice ./helm-charts/movies-service --namespace local

helm install moviesbff ./helm-charts/movies-bff --namespace local --atomic
# helm upgrade moviesbff ./helm-charts/movies-bff --namespace local

helm install moviesfrontend ./helm-charts/movies-frontend --namespace local --atomic
# helm upgrade moviesfrontend ./helm-charts/movies-frontend --namespace local
```

# Resources
https://stackoverflow.com/questions/74493358/docker-manifest-unknown-from-local-docker-registry
https://hasura.io/blog/sharing-a-local-registry-for-minikube-37c7240d0615/
https://kubernetes.io/docs/concepts/services-networking/ingress/#examples