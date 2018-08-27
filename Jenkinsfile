node {
    stage('Prepare') {
        properties([authorizationMatrix(['hudson.model.Item.Discover:authenticated', 'hudson.model.Item.Read:authenticated', 'hudson.model.Item.ViewStatus:authenticated']), buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')),
                    disableConcurrentBuilds(), pipelineTriggers([[$class: 'PeriodicFolderTrigger', interval: '1d']])])
        checkout scm
        sh './gradlew clean'
    }

    stage('Build') {
        sh './gradlew build'
    }

    stage('Test') {
        sh './gradlew test'
        junit "build/test-results/*/*.xml"
    }

    stage('jar') {
        sh './gradlew jar'
    }

    stage('Archive') {
        archiveArtifacts 'build/libs/*.jar'
        //TODO put on some repo
    }

}