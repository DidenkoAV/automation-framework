pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'git@github.com:DidenkoAV/automation-framework.git', credentialsId: '15650df1-1827-455a-a281-90b6be7766bc'
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
