def call() {

    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    sh "./ngw build"
                }
            }
            stage('Test') {
                steps {
                    sh "./ngw 'test --no-watch --no-progress --browsers=ChromeHeadlessCI'"
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