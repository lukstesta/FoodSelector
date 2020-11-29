pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                sh './gradlew clean'
                sh './gradlew assembleDebug'
            }
        }
        stage ('Unit Tests') {
            steps {
                sh './gradlew testDebugUnitTest'
            }
        }
    }
}