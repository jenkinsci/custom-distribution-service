List platforms = ['linux']
Map branches = [:]

for (int i = 0; i < platforms.size(); ++i) {
    String label = platforms[i]
    branches[label] = {
        node(label) {
            timestamps {
                stage('Checkout') {
                    checkout scm
                }

                stage('Build') {
                    withEnv([
                        "JAVA_HOME=${tool 'jdk8'}",
                        "PATH+MVN=${tool 'mvn'}/bin",
                        'PATH+JDK=$JAVA_HOME/bin',
                    ]) {
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

                stage('Archive') {
                    /* Archive the test results */
                    junit '**/target/surefire-reports/TEST-*.xml'

                    if (label == 'linux') {
                      archiveArtifacts allowEmptyArchive: true, artifacts: '**/target/**/*.jar
                      findbugs pattern: '**/target/findbugsXml.xml'
                    }
                }
            }
        }
    }
}

/* Execute our platforms in parallel */
parallel(branches)