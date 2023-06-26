# ForFun_CurrencyConversionMicroservice

How to build dockers:

mvn spring-boot:build-image -DskipTests

<img width="1276" alt="Build docker message" src="https://github.com/lebronjamesuit/ForFun_CurrencyConversionMicroservice/assets/11584601/b0e6ab7a-f3d2-48ce-98a1-17420f1b6495">
<img width="762" alt="Screenshot 2023-06-22 at 11 52 34" src="https://github.com/lebronjamesuit/ForFun_CurrencyConversionMicroservice/assets/11584601/50af7511-65bb-4809-aa4e-50fdea0ae932">


Result after deploy microservice to Kubenertes

NAME                                       READY   STATUS    RESTARTS   AGE
pod/currency-conversion-67947f565f-882vt   1/1     Running   0          7m10s
pod/currency-conversion-67947f565f-p6jdx   1/1     Running   0          7m10s
pod/currency-conversion-67947f565f-pf9lf   1/1     Running   0          7m10s
pod/currency-exchange-86984bc99b-57jjc     1/1     Running   0          85m
pod/currency-exchange-86984bc99b-dh89s     1/1     Running   0          85m
pod/currency-exchange-86984bc99b-vk2q2     1/1     Running   0          85m

NAME                          TYPE           CLUSTER-IP    EXTERNAL-IP      PORT(S)          AGE
service/currency-conversion   LoadBalancer   10.0.1.7      35.239.207.222   8100:31742/TCP   4m14s
service/currency-exchange     LoadBalancer   10.0.12.127   35.184.83.147    8000:30215/TCP   82m
service/kubernetes            ClusterIP      10.0.0.1      <none>           443/TCP          3d8h

NAME                                  READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/currency-conversion   3/3     3            3           7m11s
deployment.apps/currency-exchange     3/3     3            3           85m

NAME                                             DESIRED   CURRENT   READY   AGE
replicaset.apps/currency-conversion-67947f565f   3         3         3       7m11s
replicaset.apps/currency-exchange-86984bc99b     3         3         3       85m