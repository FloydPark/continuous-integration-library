package com.floydpark.devops.ci

class DockerUtil {

    def getTagVersion(env, PROJECT_VERSION) {

        def branch = env.BRANCH_NAME
        def projectVersion = PROJECT_VERSION
        def version = ''
        if (branch.equals('develop')) {

            version = projectVersion + '.dev.' + env.BUILD_ID
        } else if (branch.startsWith('feature/')) {

            def branchName = branch.split("/")[1]
            version = projectVersion + '.ft-' + branchName + '.' + env.BUILD_ID
        } else if (branch.startsWith('bugfix/')) {

            def branchName = branch.split("/")[1]
            version = projectVersion + '.bf-' + branchName + '.' + env.BUILD_ID
        } else if (branch.startsWith('PR')) {

            version = projectVersion + '.' + branch + '.' + env.BUILD_ID
        } else if (branch.equals('master')) {

            version = projectVersion
        }

        return version
    }
}
