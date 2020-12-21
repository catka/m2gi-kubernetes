mvn clean package;
kubectl delete deployment fight-service;
kubectl delete service fight-service;
docker build -f src/main/docker/Dockerfile.jvm -t workshop/fight-service .;
kubectl apply -f kubernetes/;