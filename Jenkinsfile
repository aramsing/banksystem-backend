pipeline {

    // will find any available agent to perform any action (stage)
    agent any

    environment {
        dockerHub = credentials('dockerHub') // credentials id in jenkins
        DBPASS = credentials('DBPASS')
    }

    // define stages
    stages {
        stage('Build & Deliver') {
            steps {
                sh "docker login -u ${dockerHub_USR} -p ${dockerHub_PSW}"
                sh "docker build -t arjunramsinghani/banksystem-backend:1.0.0 ."
                sh "docker push arjunramsinghani/banksystem-backend:1.0.0" // push image to dockerhub
            }
        }
        stage('Deploy') {
            steps {
                sh "docker run -e DBPASS=${DBPASS} -d -p 8888:9999 arjunramsinghani/banksystem-backend:1.0.0" // e flag for environment variables and for appliction.yaml file to have context
            }
        }
    }
}