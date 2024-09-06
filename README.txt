Install jenkins with docker:
    1. Use jenkins.tar image:
       docker load -i jenkins.tar
       docker login
       docker tag my-java-maven-jenkins:latest <dockerhub-username>/my-java-maven-jenkins:latest
       docker push <dockerhub-username>/my-java-maven-jenkins:latest
       docker run -d --name my-jenkins-container -p 8080:8080 -p 50000:50000 <dockerhub-username>/my-java-maven-jenkins:latest








