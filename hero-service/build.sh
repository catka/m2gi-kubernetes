mvn clean package;
kubectl delete deployment hero-service;
kubectl delete service hero-service;
kubectl delete configmap hero-config;
kubectl delete deployment hero-pg;
docker build -f src/main/docker/Dockerfile.jvm -t workshop/hero-service .;
kubectl apply -f kubernetes/;