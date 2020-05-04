def call() {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    sh './gradlew build -x test'
                }
            }
            stage('Test') {
                steps {
                    sh './gradlew test'
                }
            }
            stage('Code analysis') {
                withSonarQubeEnv("SonarCloud") {
                    sh './gradlew sonarqube'
                }
            }
            stage('Package and Upload artifact') {
                steps {
                    echo 'In progress...'
                }
            }
        }
    }
}