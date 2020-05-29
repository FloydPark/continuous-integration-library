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
                steps {
                    script {
                        def projectName = sh "./gradlew properties | grep 'name:' | awk {'print \$2'}"
                        archivaPublish(env, projectName)
                    }  
                }
            }            
        }
    }
}