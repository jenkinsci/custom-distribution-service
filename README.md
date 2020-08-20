# Jenkins Custom Distribution Service


[![Gitter](https://badges.gitter.im/jenkinsci/jenkins-custom-distribution-service.svg)](https://gitter.im/jenkinsci/jenkins-custom-distribution-service?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/b4fea9e79e2a485a929ed7aa71b222a1)](https://www.codacy.com/gh/jenkinsci/custom-distribution-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jenkinsci/custom-distribution-service&amp;utm_campaign=Badge_Grade)
[![GitHub license](https://img.shields.io/github/license/jenkinsci/custom-distribution-service)](https://github.com/jenkinsci/custom-distribution-service/blob/master/LICENSE)


## Overview
The main idea behind the project is to build a customizable Jenkins distribution service that could be used to build 
tailor-made Jenkins distributions. The service would provide users with a simple interface to select the configurations 
they want to build the instance with eg: plugins, authorization matrices etc. Furthermore it would include a section 
for sharing community created distros so that users can find and download already built Jenkins war/configuration files 
to use out of the box.


## QuickStart

You can spin up the entire web application using a docker compose file command: 
The front-end runs on `localhost:3001` and the backend  runs on `localhost:8080`.

Build the containers: 
```
docker-compose up -d --build
```

Run the containers: 
```
docker-compose up
```

## Useful Links

a) [Project page](https://www.jenkins.io/projects/gsoc/2020/projects/custom-jenkins-distribution-build-service/)

b) [GSoC Proposal](https://docs.google.com/document/d/1C7VQJ92Yhr0KRDcNVHYxn4ri7OL9IGZmgxY6UFON6-g/edit?usp=sharing)

c) [Design Document](https://docs.google.com/document/d/1-ujWVJ2a5VYkUF6UA7m4bEpSDxmb3mJZhCbmoKO716U/edit?usp=sharing)
