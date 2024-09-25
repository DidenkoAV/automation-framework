pipeline {
    agent any
    environment {
        MAVEN_HOME = '/opt/homebrew/Cellar/maven/3.9.9'
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        ALLURE_HISTORY_DIR = 'allure_history'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'git@github.com:DidenkoAV/automation-framework.git', credentialsId: '2c3f7cc8-11a5-495b-a9f4-a45a06063136'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test -Dsurefire.suiteXmlFiles=testng.xml -Dbrowser=chrome'
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh 'mvn allure:report'
                sh "mkdir -p ${ALLURE_HISTORY_DIR}"
                sh "cp -r target/allure-results/* ${ALLURE_HISTORY_DIR}/"
            }
        }
    }

    post {
        always {
           // archiveArtifacts artifacts: 'target/allure-results/**'
            allure includeProperties: false, results: [[path: 'target/allure-results']]
        }
    }
}
