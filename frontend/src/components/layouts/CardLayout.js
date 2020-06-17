import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col } from 'reactstrap';


class CardLayout extends React.Component {

    state = {
        plugins: {
            "42crunch-security-audit":{
                "buildDate":"May 28, 2020",
                "dependencies":[
                   {
                      "name":"apache-httpcomponents-client-4-api",
                      "optional":false,
                      "version":"4.5.10-1.0"
                   },
                   {
                      "name":"credentials",
                      "optional":false,
                      "version":"2.1.19"
                   },
                   {
                      "name":"structs",
                      "optional":false,
                      "version":"1.20"
                   }
                ],
                "developers":[
                   {
                      "developerId":"anton"
                   }
                ],
                "excerpt":"Performs API contract security audit to get a detailed analysis of the possible vulnerabilities and other issues in the API contract.",
                "gav":"io.jenkins.plugins:42crunch-security-audit:3.2",
                "labels":[
                   "analysis",
                   "security",
                   "test"
                ],
                "minimumJavaVersion":"1.8",
                "name":"42crunch-security-audit",
                "popularity":0,
                "previousTimestamp":"2020-05-18T18:06:16.00Z",
                "previousVersion":"3.1",
                "releaseTimestamp":"2020-05-28T00:28:48.00Z",
                "requiredCore":"2.164.3",
                "scm":"https://github.com/jenkinsci/42crunch-security-audit-plugin",
                "sha1":"6EJVYIADcV9ucASmVg+bUDW29vU=",
                "sha256":"Toll5Mdr6t9F74Z0HP8md5/gSw6Jua3Erwxydi0DBj4=",
                "title":"42Crunch REST API Static Security Testing",
                "url":"http://updates.jenkins-ci.org/download/plugins/42crunch-security-audit/3.2/42crunch-security-audit.hpi",
                "version":"3.2",
                "wiki":"https://plugins.jenkins.io/42crunch-security-audit"
             },
             "AnchorChain":{
                "buildDate":"Mar 11, 2012",
                "dependencies":[
       
                ],
                "developers":[
                   {
                      "developerId":"direvius",
                      "email":"direvius@gmail.com",
                      "name":"Alexey Lavrenuke"
                   }
                ],
                "excerpt":"Adds some links to the sidebar at every build. The data are obtained from a user selected file in a working directory.",
                "gav":"org.jenkins-ci.plugins:AnchorChain:1.0",
                "labels":[
                   "report"
                ],
                "name":"AnchorChain",
                "popularity":578,
                "releaseTimestamp":"2012-03-11T14:59:11.00Z",
                "requiredCore":"1.398",
                "scm":"https://github.com/jenkinsci/anchor-chain-plugin",
                "sha1":"rY1W96ad9TJI1F3phFG8X4LE26Q=",
                "sha256":"kmW5FrU9RG3O06JRLJt+1YGU0rB6pQoaZ57Vzm6Oc1s=",
                "title":"AnchorChain",
                "url":"http://updates.jenkins-ci.org/download/plugins/AnchorChain/1.0/AnchorChain.hpi",
                "version":"1.0",
                "wiki":"https://plugins.jenkins.io/AnchorChain"
             }
        }
    }

    async componentDidMount() {
        const response = await fetch('/api/plugin/getPluginList');
        const body = await response.json();
        const mainBody = body["plugins"]
        console.log(mainBody)
        this.setState({ plugins: mainBody});
    }

    render () {

        let pluginCards = Object.keys(this.state.plugins).map(key => {
            console.log(this.state.plugins[key])
            return(
                <Col sm="3">
                    <PluginCard plugin = {this.state.plugins[key]} />
                </Col>
            )
        });

        return (
            <Container fluid>
                <Row>
                   {pluginCards} 
                </Row>
            </Container>
        )
    }
}

export default CardLayout;
