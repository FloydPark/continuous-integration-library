def call() {
    pipeline {
        agent any
        environment {            
            URL_ARCHIVA_SNAPSHOTS = "${URL_ARCHIVA_SNAPSHOTS}"
            URL_ARCHIVA_RELEASE = "${URL_ARCHIVA_RELEASE}"
            ARCHIVA_CREDS = credentials('ARCHIVA_CREDS')
            PROJECT_NAME = ""
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
                 when {
                     not {
                         anyOf {

                             branch "feature/*"
                             branch "bugfix/*"
                         }
                     }
                 }
                 steps{
                     sonarqubeGradle(env)
                 }
             }
            stage('Package and Upload artifact') {
                environment {

                    PROJECT_VERSION = sh (script: './gradlew properties | grep \'version:\' | awk \'{print $2}\'', returnStdout: true).trim()
                }
                steps {

                    archivaPublish(env, PROJECT_VERSION)
                }
            }
        }
    }
}