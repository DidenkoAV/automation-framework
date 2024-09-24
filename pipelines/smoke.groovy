pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'git@github.com:DidenkoAV/automation-framework.git', credentialsId: '2c3f7cc8-11a5-495b-a9f4-a45a06063136'
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
