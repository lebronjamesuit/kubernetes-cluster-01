How to deploy microservices to kubernetes

1 . Build image jamesvo/mss-currency-exchange-k8s:0.0.11-SNAPSHOT
2.  Docker push ...


kubectl version

kubectl --version

kubectl create deployment  currency-exchange --image=jamesfloatingmarket1508/mss-currency-exchange-k8s:0.0.11-SNAPSHOT


kubectl expose deployment currency-exchange --type=LoadBalancer --port=8000


kubectl get service
kubectl get pods


NAME                      TYPE           CLUSTER-IP    EXTERNAL-IP      PORT(S)          AGE
currency-exchange         LoadBalancer   10.0.9.149    34.30.3.129      8000:30215/TCP   

#Test that url
curl http://34.30.3.129:8000/currency-exchange/from/USD/to/GBP


# Conversion 
kubectl create deployment currency-conversion  --image=jamesfloatingmarket1508/mss-currency-conversion-k8s:0.0.11-SNAPSHOT


kubectl expose deployment currency-conversion  --type=LoadBalancer --port=8100

NAME                      TYPE           CLUSTER-IP    EXTERNAL-IP      PORT(S)          AGE
currency-conversion       LoadBalancer   10.0.12.177   35.184.83.147    8100:30563/TCP   85s

http://localhost:8100/feign/currency-conversion/from/USD/to/GBP/quanlity/111

curl http://35.184.83.147:8100/feign/currency-conversion/from/USD/to/VND/quanlity/111
							 feign/currency-conversion/from/USD/to/VND/quanlity/12

curl http://35.184.83.147:8100

# how to update image for deployment

syntax: kubectl set image deployment/myapp-deployment myapp-container=newimage:latest
apply:  kubectl set image deployment/currency-conversion mss-currency-conversion-k8s=jamesfloatingmarket1508/mss-currency-conversion-k8s:0.0.11-SNAPSHOT


# Re-deploy
kubect delete all -l app=currency-exchange

	# result
	pod "currency-exchange-86984bc99b-29pxv" deleted
	pod "currency-exchange-86984bc99b-jcwp8" deleted
	pod "currency-exchange-86984bc99b-jp77v" deleted
	service "currency-exchange" deleted
	deployment.apps "currency-exchange" deleted


# From now, I only need to kubectl apply -f deployment.yaml to deploy everything.
NAME                        TYPE           CLUSTER-IP    EXTERNAL-IP     PORT(S)          AGE
service/currency-exchange   LoadBalancer   10.0.12.127   35.184.83.147   8000:30215/TCP   3m1s


I run it 6 times to see Load balancing between 3 replicaset.
curl http://35.184.83.147:8000/currency-exchange/from/USD/to/GBP




# What about ConfigMap

kubectl create configmap <configmap-name> --from-literal=<key1>=<value1> --from-literal=<key2>=<value2>

add config map to yaml file







