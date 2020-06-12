def call() {
    pipeline {
        agent any
        stages {
            stage('Updating dependencies'){
                steps {
                    sh "npm i"
                }
            }
            stage('Build') {
                steps {
                    sh "ng build"
                }
            }
            stage('Test') {
                steps {
                    sh "ng test --no-watch --no-progress --browsers=ChromeHeadlessCI"
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
                    sonarqubeJS(env)
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