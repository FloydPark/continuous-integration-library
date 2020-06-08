def call() {
    pipeline {
        agent any
        environment {
            URL_ARCHIVA_SNAPSHOTS = "${URL_ARCHIVA_SNAPSHOTS}"
            URL_ARCHIVA_RELEASE = "${URL_ARCHIVA_RELEASE}"
            ARCHIVA_CREDS = credentials('ARCHIVA_CREDS')
        }
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
            stage('Package and Upload artifact') {
                environment {

                    PROJECT_VERSION = sh (script: './gradlew properties | grep \'version:\' | awk \'{print $2}\'', returnStdout: true).trim()
                }
                steps {

                    sh "./gradlew zipSPA"
                    archivaPublish(env, PROJECT_VERSION)
                    sh "./gradlew cleanBuild"
                }
            }
        }
    }
}