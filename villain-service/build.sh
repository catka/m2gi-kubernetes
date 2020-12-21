mvn clean package;
kubectl delete deployment villain-service;
kubectl delete service villain-service;
kubectl delete configmap villain-config;
kubectl delete deployment villain-pg;
docker build -f src/main/docker/Dockerfile.jvm -t workshop/villain-service .;
kubectl apply -f kubernetes/;