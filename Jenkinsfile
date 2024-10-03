pipeline {

    // will find any available agent to perform any action (stage)
    agent any


    // define stages
    stages {
        stage('Build & Deliver') {
            steps {
                sh "docker build -t arjunramsinghani/banksystem-backend:1.0.0 ."
                sh "docker push arjunramsinghani/banksystem-backend:1.0.0" // push image to dockerhub
            }
        }
        stage('Deploy') {
            steps {
                sh "docker run -d -p 8080:8080 arjunramsinghani/banksystem-backend:1.0.0" // e flag for environment variables and for appliction.yaml file to have context
            }
        }
    }
}