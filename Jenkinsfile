pipeline {
    stages {
        stage('Build') {
            steps {
                sh './mvn spring-boot:run'
            }
        }
        stage('Test') {
            steps {
                sh ' ./mvn test'
            }
        }
    }
}