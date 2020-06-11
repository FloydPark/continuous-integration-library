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
                    sh './gradlew clean'
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
            stage('Build Docker Image and Push it to Registry') {
                environment {

                    PROJECT_VERSION = sh (script: './gradlew properties | grep \'version:\' | awk \'{print $2}\'', returnStdout: true).trim()
                    PROJECT_NAME = sh (script: './gradlew properties | grep \'name:\' | awk \'{print $2}\'', returnStdout: true).trim()
                }
                steps {
                    script {

                        dockerResolver(env, PROJECT_VERSION, PROJECT_NAME)
                    }
                }
            }
        }
    }
}