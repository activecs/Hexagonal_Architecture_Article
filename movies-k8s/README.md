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
#minikube addons enable registry
#minikube addons enable registry-aliases
#watch minikube ssh -- cat /etc/hosts 
#minikube config set insecure-registry "192.168.49.0/24"
#minikube addons enable ingress-dns
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
## 2.1 Create Docker image
```bash
./build-docker-image.sh
```
## 2.2 Deploy to K8s manually
```bash
kubectl delete -f 5-deploy-movies-service.yaml
kubectl delete -f 6-deploy-movies-bff.yaml
kubectl delete -f 7-deploy-movies-frontend.yaml

kubectl apply -f 5-deploy-movies-service.yaml
kubectl apply -f 6-deploy-movies-bff.yaml
kubectl apply -f 7-deploy-movies-frontend.yaml
```

# Automated Deployment (WIP)
# 3.1 Deploy to K8s using HELM
```bash
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

# 4.1 Access the application
```bash
# http://moviesfrontend
```

# Resources
https://stackoverflow.com/questions/74493358/docker-manifest-unknown-from-local-docker-registry
https://hasura.io/blog/sharing-a-local-registry-for-minikube-37c7240d0615/
https://kubernetes.io/docs/concepts/services-networking/ingress/#examples