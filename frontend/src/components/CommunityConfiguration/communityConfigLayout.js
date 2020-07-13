import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col, Button} from 'reactstrap';
import { Pagination, PaginationItem, PaginationLink, Spinner } from 'reactstrap';
import ConfigurationCard from './configurationCard';

class communityConfigLayout extends React.Component {

    state = {
        search:"",
        data: [
            {
            "name": "blueocean-zh.yaml",
            "path": "formulas/blueocean-zh.yaml",
            "sha": "bfb6e9a002bc04f98e8d5d7e68b69511bcc1f71e",
            "size": 5204,
            "url": "https://api.github.com/repos/jenkins-zh/jenkins-formulas/contents/formulas/blueocean-zh.yaml?ref=master",
            "html_url": "https://github.com/jenkins-zh/jenkins-formulas/blob/master/formulas/blueocean-zh.yaml",
            "git_url": "https://api.github.com/repos/jenkins-zh/jenkins-formulas/git/blobs/bfb6e9a002bc04f98e8d5d7e68b69511bcc1f71e",
            "download_url": "https://raw.githubusercontent.com/jenkins-zh/jenkins-formulas/master/formulas/blueocean-zh.yaml",
            "type": "file",
            "_links": {
                "self": "https://api.github.com/repos/jenkins-zh/jenkins-formulas/contents/formulas/blueocean-zh.yaml?ref=master",
                "git": "https://api.github.com/repos/jenkins-zh/jenkins-formulas/git/blobs/bfb6e9a002bc04f98e8d5d7e68b69511bcc1f71e",
                "html": "https://github.com/jenkins-zh/jenkins-formulas/blob/master/formulas/blueocean-zh.yaml"
            },
        },
        {
            "name": "k8s.yaml",
            "path": "formulas/k8s.yaml",
            "sha": "f6306ede1b0e8b7d981db9f9d26de5a06e54a682",
            "size": 4146,
            "url": "https://api.github.com/repos/jenkins-zh/jenkins-formulas/contents/formulas/k8s.yaml?ref=master",
            "html_url": "https://github.com/jenkins-zh/jenkins-formulas/blob/master/formulas/k8s.yaml",
            "git_url": "https://api.github.com/repos/jenkins-zh/jenkins-formulas/git/blobs/f6306ede1b0e8b7d981db9f9d26de5a06e54a682",
            "download_url": "https://raw.githubusercontent.com/jenkins-zh/jenkins-formulas/master/formulas/k8s.yaml",
            "type": "file",
            "_links": {
                "self": "https://api.github.com/repos/jenkins-zh/jenkins-formulas/contents/formulas/k8s.yaml?ref=master",
                "git": "https://api.github.com/repos/jenkins-zh/jenkins-formulas/git/blobs/f6306ede1b0e8b7d981db9f9d26de5a06e54a682",
                "html": "https://github.com/jenkins-zh/jenkins-formulas/blob/master/formulas/k8s.yaml"
            }
        }
        ],
        isLoading: true
    }

    render() {
        let configurationCards;
        configurationCards = this.state.data.map(config => {
            return(
                <Col sm="3">
                    <ConfigurationCard config = {config} /> 
                </Col>
            )
        }) 

        return(
            <Container fluid style = {{height: "100vh"}}>
            <Row>
                {configurationCards} 
             </Row>
            </Container> 
        )

    }
}

export default communityConfigLayout;
