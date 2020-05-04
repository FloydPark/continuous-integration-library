import com.floydpark.devops.ci.SonarQubeUtil

def call(env){

    def sonarQubeUtil = new SonarQubeUtil()

    withSonarQubeEnv("SonarCloud") {
        sh './gradlew sonarqube '+sonarQubeUtil.getProperties(env)
    }
}