def call() {
    pipeline {
        agent any
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