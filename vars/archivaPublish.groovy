import com.floydpark.devops.ci.ArchivaUtil

def call(env){

    def archivaUtil = new ArchivaUtil()    
    sh './gradlew publish '+archivaUtil.getPublishProperties(env)
}