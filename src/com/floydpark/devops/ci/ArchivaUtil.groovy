package com.floydpark.devops.ci

class ArchivaUtil {

    def getPublishProperties(env){
        
        def projectName = env.PROJECT_NAME
        def branch = env.BRANCH_NAME
        //def javaProjectVersions = readJSON text: env.JAVA_PROJECT_VERSIONS.toString()
        def javaProjectVersions = env.JAVA_PROJECT_VERSIONS
        def projectVersion = javaProjectVersions[projectName]
        def urlArchiva = branch.equals('master') ? env.URL_ARCHIVA_RELEASE : env.URL_ARCHIVA_SNAPSHOTS;
        def branchVersion = getBranchVersion(branch, projectVersion)
        def properties = "-Partifact_version=${branchVersion} -Purl_archiva=${urlArchiva} -Pusr_archiva=${env.ARCHIVA_CREDS_USR} -Ppsw_archiva=${env.ARCHIVA_CREDS_PSW}"
        return properties;
    }

    private def getBranchVersion(branch, projectVersion){

        def version = ''
        if(branch.equals('develop')){
            
            version = projectVersion+'.dev-SNAPSHOT'
        }
        else
            if(branch.startsWith('feature/')){

                def branchName = branch.split("/")[1]
                version = projectVersion+'ft-'+branchName+'-SNAPSHOT'
            }
            else
                if(branch.startsWith('bugfix/')){

                    def branchName = branch.split("/")[1]
                    version = projectVersion+'bf-'+branchName+'-SNAPSHOT'
                }
                else
                    if(branch.equals('master')){

                        version = projectVersion
                    }

        return version
    }
}