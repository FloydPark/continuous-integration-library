def call(env){

    withSonarQubeEnv("SonarCloud") {
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
        if(branch.contains('pr')){

            properties = "-Dsonar.pullrequest.base=develop"
            properties = "$properties -Dsonar.pullrequest.base=$branch "
        }

    return properties;
}