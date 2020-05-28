def call() {
    pipeline {
        agent any
        environment {            
            URL_ARCHIVA_SNAPSHOTS = "${URL_ARCHIVA_SNAPSHOTS}"
            URL_ARCHIVA_RELEASE = "${URL_ARCHIVA_RELEASE}"
            ARCHIVA_CREDS = credentials('ARCHIVA_CREDS')
        }
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