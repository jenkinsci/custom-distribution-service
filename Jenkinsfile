pipeline {
    agent linux 
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
                                sh 'mvn --batch-mode clean install -Denvironment=test -Prun-its'
                            }
                            else {
                                bat 'mvn --batch-mode clean install -Denvironment=test -Prun-its'
                            }
                        }
                    }
                }
             }
        }

        stage('React Build') {
            agent {
            docker {
                label 'linux'
                image 'node:6-alpine'
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