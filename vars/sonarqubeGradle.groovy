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
        if(branch.startsWith('PR-')){
            def key = branch.split("-")[1]
            branch = env.CHANGE_BRANCH
            properties = "-Dsonar.pullrequest.base=develop"
            properties = "$properties -Dsonar.pullrequest.branch=$branch"
            properties = "$properties -Dsonar.pullrequest.key=$key"
        }

    return properties;
}