def call() {

    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    echo 'In progress...'
                }
            }
            stage('Test') {
                steps {
                    echo 'In progress...'
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