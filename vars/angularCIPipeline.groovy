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
                steps{
                    sonarqubeJS(env)
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