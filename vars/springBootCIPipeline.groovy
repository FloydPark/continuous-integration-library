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
                steps{
                    sonarqubeGradle(env)
                }
            }
            stage('Package and Upload artifact') {
                steps {
                    archivaPublish(env)
                }
            }            
        }
    }
}