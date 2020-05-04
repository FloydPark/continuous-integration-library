package com.floydpark.devops.ci

class SonarQubeUtil {

    def getProperties(env){

        def properties = "";
        def branch = env.BRANCH_NAME;

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
}
