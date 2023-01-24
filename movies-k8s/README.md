# Local Deployment

# 1.1 Prerequisites: Install minikube with dashboard
```shell
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

minikube start
#minikube addons list
minikube addons enable ingress
minikube addons enable ingress-dns
minikube addons enable dashboard
minikube addons enable metrics-server
#minikube ip
#minikube dashboard --url

```
# 1.2 Prerequisites: Install HELM
```shell
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh
```
# 2 Create Docker image
```shell
./build-docker-image.sh
```
# 3.1 Deploy to K8s manually
```shell
kubectl apply -f 3-namespace.yaml
kubectl -n local apply -f 5-deploy-movies-service.yaml
```
# 3.2 Deploy to K8s using HELM
```shell
helm install moviesservice ./helm-charts/movies-service --namespace local --atomic
# helm upgrade moviesservice ./helm-charts/movies-service --namespace local

helm install moviesbff ./helm-charts/movies-bff --namespace local --atomic
# helm upgrade moviesbff ./helm-charts/movies-bff --namespace local

helm install moviesfrontend ./helm-charts/movies-frontend --namespace local --atomic
# helm upgrade moviesfrontend ./helm-charts/movies-frontend --namespace local

# kubectl port-forward service/moviesservice :8080 -n local
# kubectl port-forward service/moviesbff :8080 -n local
# kubectl port-forward service/moviesfrontend :8050 -n local
```