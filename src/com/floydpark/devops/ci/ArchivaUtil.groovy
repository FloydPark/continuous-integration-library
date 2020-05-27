package com.floydpark.devops.ci

class ArchivaUtil {

    def getPublishProperties(env){
        
        def branch = env.BRANCH_NAME;
        def urlArchiva = branch.equals('master') ? env.URL_ARCHIVA_RELEASE : env.URL_ARCHIVA_SNAPSHOTS;
        def properties = "-Purl_archiva=${urlArchiva} -Pusr_archiva=${env.ARCHIVA_CREDS_USR} -Ppsw_archiva=${env.ARCHIVA_CREDS_PSW}"
        return properties;
    }
}
