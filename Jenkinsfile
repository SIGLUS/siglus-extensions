pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '50'))
    }
    environment {
        IMAGE_REPO = "siglusdevops/siglusextensions"
    }
    stages {
        stage('Check') {
            steps {
                sh './gradlew clean check'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Push Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: "cad2f741-7b1e-4ddd-b5ca-2959d40f62c2", usernameVariable: "USER", passwordVariable: "PASS")]) {
                    sh '''
                        set +x
                        docker login -u $USER -p $PASS
                        IMAGE_TAG=${BRANCH_NAME}-$(git rev-parse HEAD)
                        IMAGE_NAME=${IMAGE_REPO}:${IMAGE_TAG}
                        docker build -t ${IMAGE_NAME} .
                        docker push ${IMAGE_NAME}
                        docker rmi ${IMAGE_NAME}
                    '''
                }
            }
        }
    }
}
