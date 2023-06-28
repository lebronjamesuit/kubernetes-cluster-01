

# Deplo microservice to GKE (Google Kubernetes Engine)

# Maven
  Build image for Dcoker Hub from Maven
  mvn spring-boot:build-image -DskipTests

# Docker Images: 
  1)  You can pull docker image to your OWN local machine
    -- Exchange --- 
    "docker pull jamesfloatingmarket1508/mss-currency-exchange-k8s:0.0.11-SNAPSHOT"
    
    -- Conversion -- 
    "docker pull jamesfloatingmarket1508/mss-currency-conversion-k8s:0.0.11-SNAPSHOT"
  
  2) Run docker containers in your local manchine by Docker Deamon

   "docker run -p 8000:8000 jamesfloatingmarket1508/mss-currency-exchange-k8s:0.0.11-SNAPSHOT"

   "docker run -p 8100:8100 jamesfloatingmarket1508/mss-currency-conversion-k8s:0.0.11-SNAPSHOT"
         
  3.  Test command on local:
     curl http://localhost:8000/currency-exchange/from/USD/to/GBP
  
     curl  http://localhost:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/70

# Deploy manual or automatic by yaml file to deployment.yaml to Google Kubernetes Engine

Create trial Google Kubernetes Cluster 

<img width="1233" alt="Screenshot 2023-06-28 at 14 52 06" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/5d3c69d4-7c17-430a-b55b-a2ea9273e4ff">

a) Manual: 

a.1) Exchange service

kubectl create deployment currency-exchange --image=jamesfloatingmarket1508/mss-currency-exchange-k8s:0.0.11-SNAPSHOT


kubectl expose deployment currency-exchange --type=LoadBalancer --port=8000

<img width="1008" alt="Screenshot 2023-06-28 at 15 00 40" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/ed209d67-f1dd-4746-a435-d06761e78e20">

kubectl get service

NAME                TYPE           CLUSTER-IP   EXTERNAL-IP    PORT(S)          AGE
currency-exchange   LoadBalancer   10.0.6.199   34.31.250.64   8000:30513/TCP   2m14s
kubernetes          ClusterIP      10.0.0.1     <none>         443/TCP          5d6h


curl http://EXTERNAL-IP:port/currency-exchange/from/USD/to/GBP

curl http://34.31.250.64:8000/currency-exchange/from/USD/to/GBP

a.2) Conversion service


kubectl create deployment currency-conversion --image=jamesfloatingmarket1508/mss-currency-conversion-k8s:0.0.11-SNAPSHOT


kubectl expose deployment currency-conversion --type=LoadBalancer --port=8100


NAME                  TYPE           CLUSTER-IP   EXTERNAL-IP     PORT(S)          AGE
currency-conversion   LoadBalancer   10.0.12.47   35.202.191.27   8100:31064/TCP   51s
kubernetes            ClusterIP      10.0.0.1     <none>          443/TCP          5d6h


curl http://35.202.191.27:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/123


b)  Deploy automatic by yaml file to Google Kubernetes Engine
Open Terminal

Step 1) 
cd to exchange-currency-k8s directory
 
 kubectl apply -f deployment.yaml

<img width="1024" alt="Screenshot 2023-06-28 at 15 16 44" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/f49a5980-fec8-4e1b-88eb-f60f97642e8c">

Step 2) 

cd to conversion-currency-k8s directory

 kubectl apply -f deployment.yaml
 
<img width="1047" alt="Screenshot 2023-06-28 at 16 19 50" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/e17002fa-f4e3-40c0-9dc8-e0a243cc1716">


# You need to run metric server to make below commands working with correct data:

kubectl get hpa

kubectl top node

kubectl top pods

kubectl get pods --all-namespaces

# Make GKN scale to 10 pods

-- Stress test
watch -n 0.01  curl http://35.202.191.27:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/123

watch -n 0.01  curl http://35.202.191.27:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/456

watch -n 0.01  curl http://35.202.191.27:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/789

<img width="1270" alt="Screenshot 2023-06-28 at 16 28 02" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/50442a7c-283b-4eb7-b95e-9b03bc5ea273">




 







  


