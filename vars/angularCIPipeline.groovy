def call() {

    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    sh "ng build"
                }
            }
            stage('Test') {
                steps {
                    sh "ng 'test --no-watch --no-progress --browsers=ChromeHeadlessCI'"
                }
            }
            stage('Code analysis') {
                steps {
                    echo 'In progress...'
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