import com.floydpark.devops.ci.DockerUtil

def call(env, PROJECT_VERSION, PROJECT_NAME){

    def dockerUtil = new DockerUtil()
    def tagVersion = dockerUtil.getTagVersion(env, PROJECT_VERSION)
    def registry = env.DOCKER_USER_REGISTRY
    def fullImageVersion = "${registry}/${PROJECT_NAME}:${tagVersion}"
    def image = docker.build(fullImageVersion)
    image.push()
    if (env.BRANCH == 'master'){

        image.push('latest')
    }
    sh "docker rmi $fullImageVersion"
}