import com.floydpark.devops.ci.ArchivaUtil

def call(env, PROJECT_VERSION){

    def archivaUtil = new ArchivaUtil()    
    sh './gradlew publish '+archivaUtil.getPublishProperties(env, PROJECT_VERSION)
}