pipeline {
    agent any 
    stages {
       
        stage('Checkout') {
            checkout scm
        }

        stage('Spring Boot Build') {
            withEnv([
                "JAVA_HOME=${tool 'jdk8'}",
                "PATH+MVN=${tool 'mvn'}/bin",
                'PATH+JDK=$JAVA_HOME/bin',
            ]) {
                List<String> mvnOptions = ['-Dmaven.test.failure.ignore','verify']
                infra.runMaven(
                    mvnOptions,
                    /*jdk*/ "8",
                    /*extraEnv*/ null,
                    /*settingsFile*/ null,
                    /*addToolEnv*/ false
                    )

                timeout(60) {
                    String command = 'mvn --batch-mode clean install -Dmaven.test.failure.ignore=true -Denvironment=test -Prun-its'
                    if (isUnix()) {
                        sh command
                    }
                    else {
                        bat command
                    }
                }
            }
        }

        stage('React Build') {
            agent {
            docker {
                image 'node:6-alpine'
                }
            }
            steps {
                sh 'npm install'
            }
        }

        stage('Archive') {
            /* Archive the test results */
            junit '**/target/surefire-reports/TEST-*.xml'
            if (label == 'linux') {
                archiveArtifacts artifacts: '**/target/**/*.jar'
                findbugs pattern: '**/target/findbugsXml.xml'
            }
        }
    }
}