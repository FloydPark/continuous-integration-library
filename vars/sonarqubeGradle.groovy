def call(env){

    withSonarQubeEnv("SonarCloud") {
        sh 'printenv'
        sh './gradlew sonarqube '+getProperties(env)
    }
}

private String getProperties(env){

    String properties = "";
    def branch = env.BRANCH_NAME;
    println(branch)

    if(branch.equals('develop') || branch.equals("master") || branch.startsWith("feature")){

        properties = "-Dsonar.branch.name=$branch"
    }
    else
        if(branch.startsWith('PR-')){
            println(env)
//            def key = branch.split("-")[1]
//            properties = "-Dsonar.pullrequest.base=develop"
//            properties = "$properties -Dsonar.pullrequest.branch=$branch "
//            properties = "$properties -Dsonar.pullrequest.key=$key "
        }

    return properties;
}