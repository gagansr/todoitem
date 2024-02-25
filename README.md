# Spring Boot Microservice Deployment on Minikube

This guide will walk you through the process of building a Docker image for your microservice and deploying it on Minikube.

## Prerequisites

- Docker installed on your local machine
- Minikube installed on your local machine
- kubectl CLI installed and configured to work with your Minikube cluster

## Steps

### 1. Start Minikube
Ensure that Minikube is running on your local machine by executing the following command:
    
```bash
minikube start
```


### 2. Dockerize Spring Boot Microservice

1. Make sure microservice project has a Dockerfile in its root directory. If not, create one. The Dockerfile should specify how to build your microservice into a Docker image.
    ```bash
   //Dockerfile
    FROM eclipse-temurin:17
    WORKDIR /app
    COPY target/TodoItem-0.0.1-SNAPSHOT.jar /app/TodoItem.jar
    ENTRYPOINT [ "java", "-jar", "TodoItem.jar" ]
    ```
2. Run below command to switch or set docker env to minikube : 
    ```bash
    eval $(minikube docker-env)
    ```

3. Build the Docker image by running the following command :

    ```bash
    docker build -t your-image-name .
    ```

   Replace `your-image-name` with the name you want to give to your Docker image.

### 3. Create Deployment
#### To deploy our docker image we have two options here
    
#### Using Command : 

    
    //create the deployment
    kubectl create deployment todoitem --image=todoitemimg:1.0 --port=8080
    
    //create the service
    kubectl expose deployment todoitem --type=ClusterIP
    
#### OR
    
#### Using YAML file : 

     
    //deployment.yml
    apiVersion: apps/v1
    kind: Deployment
     metadata:
         name: todoitem
     spec:
         replicas: 3
     selector:
         matchLabels:
             app: todoitem
     template:
         metadata:
             labels:
                 app: todoitem
     spec:
         containers:
             - name: todoitem
              image: todoitem:1.1
              imagePullPolicy: IfNotPresent
              ports:
                 - containerPort: 8080

     //service.yaml
     apiVersion: v1
     kind: Service
     metadata:
         name: todoitem
     spec:
         selector:
             app: todoitem
         ports:
             - protocol: TCP
             port: 8080
             targetPort: 8080
             type: ClusterIP

    
    
    
### 4. Get URL after running below command


```bash
minikube service todoitem 
```

This will return URL Like http://127.0.0.1:55858 where our spring boot app is UP and Running
