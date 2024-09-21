pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/DidenkoAV/automation-framework.git', credentialsId: '42824942-dce7-4395-85f1-3d43af0c81fa'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                // Run TestNG using the testng.xml file
                sh 'mvn test -Dsurefire.suiteXmlFiles=testng.xml'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            allure([
                    results: [[path: 'target/allure-results']],
                    report: 'target/allure-report'
            ])
        }
    }
}
