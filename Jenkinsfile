pipeline {
    agent any 
    stages {
        stage('Checkout') {
            steps{
             checkout scm
            }
        }

        stage('Spring Boot Build') {
            steps {
                withEnv([
                    "JAVA_HOME=${tool 'jdk8'}",
                    "PATH+MVN=${tool 'mvn'}/bin",
                    'PATH+JDK=$JAVA_HOME/bin',
                ]) {
                    
                    timeout(60) {
                        script {
                            List<String> mvnOptions = ['-Dmaven.test.failure.ignore','verify']
                            infra.runMaven(
                                mvnOptions,
                                /*jdk*/ "8",
                                /*extraEnv*/ null,
                                /*settingsFile*/ null,
                                /*addToolEnv*/ false
                            )
                            if (isUnix()) {
                                sh 'mvn --batch-mode clean install -Dmaven.test.failure.ignore=true -Denvironment=test -Prun-its'
                            }
                            else {
                                bat 'mvn --batch-mode clean install -Dmaven.test.failure.ignore=true -Denvironment=test -Prun-its'
                            }
                        }
                    }
                }
             }
        }

        stage('React Build') {
            agent {
            docker {
                image 'node:6'
                }
            }
            steps {
                sh 'npm install'
            }
        }

        stage('Archive') {
            steps {
                /* Archive the test results */
                junit '**/target/surefire-reports/TEST-*.xml'
                archiveArtifacts artifacts: '**/target/**/*.jar'
                findbugs pattern: '**/target/findbugsXml.xml'
            }
        }
    }
}