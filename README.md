

# Deploy microservice to GKE (Google Kubernetes Engine)

Key features of GCP - Google Kubernetes Engine (GKE)

    - Orchestration
    
    - Auto Scaling - Scale containers based on demand
    
    - Service Discovery - Help microservices find one another
    
    - Load Balancer - Distribute load among multiple instances of a microservice
    
    - Self Healing - Do health checks and replace failing instances
    
    - Zero Downtime Deployments - Release new versions without downtime



# 1. Start Docker Container:
  1)  You can pull docker image to your OWN local machine
     
          -Exchange microservice 
          
          docker pull jamesfloatingmarket1508/mss-currency-exchange-k8s:0.0.11-SNAPSHOT
          
          - Conversion microservice
          docker pull jamesfloatingmarket1508/mss-currency-conversion-k8s:0.0.11-SNAPSHOT
  
  2) Run docker containers in your local manchine by Docker Deamon, for testing before doing any deployment.

         docker run -p 8000:8000 jamesfloatingmarket1508/mss-currency-exchange-k8s:0.0.11-SNAPSHOT
      
         docker run -p 8100:8100 jamesfloatingmarket1508/mss-currency-conversion-k8s:0.0.11-SNAPSHOT
         
  3)  Test command on local:

           curl http://localhost:8000/currency-exchange/from/USD/to/GBP
        
           curl  http://localhost:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/70


# 2. Deploy manual or automatic Google Kubernetes Engine

Create trial Google Kubernetes Cluster 

<img width="1233" alt="Screenshot 2023-06-28 at 14 52 06" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/5d3c69d4-7c17-430a-b55b-a2ea9273e4ff">



Step 1: Make sure you have GCloud be installed in your PC.

    https://cloud.google.com/sdk/docs/install

Step 2: Authentication with Google Account that has Kubernetes Cluster.

    gcloud container clusters get-credentials {your cluster name} --zone us-central1-c --project {project id}

a) Manual: 

a1) Exchange service

    kubectl create deployment currency-exchange --image=jamesfloatingmarket1508/mss-currency-exchange-k8s:0.0.11-SNAPSHOT
    
    kubectl expose deployment currency-exchange --type=LoadBalancer --port=8000

<img width="1008" alt="Screenshot 2023-06-28 at 15 00 40" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/ed209d67-f1dd-4746-a435-d06761e78e20">

    Terminal run:  kubectl get service

    Test again: curl http://EXTERNAL-IP:port/currency-exchange/from/USD/to/GBP

    Public IP: curl http://34.31.250.64:8000/currency-exchange/from/USD/to/GBP


a2) Conversion service

    kubectl create deployment currency-conversion --image=jamesfloatingmarket1508/mss-currency-conversion-k8s:0.0.11-SNAPSHOT
    
    kubectl expose deployment currency-conversion --type=LoadBalancer --port=8100

    curl http://35.202.191.27:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/123


b) How about deploy automatic by yaml file to Google Kubernetes Engine
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

- Stress test ping API every 0.01 second to make the CPU usage hits 20%
  
      watch -n 0.01  curl http://35.202.191.27:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/123
      
      watch -n 0.01  curl http://35.202.191.27:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/456
      
      watch -n 0.01  curl http://35.202.191.27:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/789

<img width="1270" alt="Screenshot 2023-06-28 at 16 28 02" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/50442a7c-283b-4eb7-b95e-9b03bc5ea273">


# How to trace log at CLuster Engine

<img width="1275" alt="Screenshot 2023-06-28 at 16 45 25" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/8f5fa587-d7e5-4a5c-b51f-d432d0d9e4f2">

<img width="1231" alt="Screenshot 2023-06-28 at 16 58 39" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/253b693a-e920-4c66-ae94-d2a6632581f4">

Auto Scaling down to default replica set 1.
<img width="1227" alt="Screenshot 2023-06-28 at 16 59 16" src="https://github.com/lebronjamesuit/kubernetes-cluster-01/assets/11584601/e65df2bc-fbb8-4d8e-bed3-f9ac6f24b7cd">











  


