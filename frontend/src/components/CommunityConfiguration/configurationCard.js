import React from 'react'
import {
    Card, CardBody,
    CardTitle, CardSubtitle, Button
  } from 'reactstrap';
  
class configurationCard extends React.Component {

    state = {
        backgroundColor: "#185ecc",
        buttonText: "Click to view details"
    }

    constructor(props) {
        super(props)
    }
    
    async viewDetails(configName) {
        console.log(configName)
        let urlSplit = this.getCommunityConfigURL().split('/')
        const response = await fetch('https://raw.githubusercontent.com/' + urlSplit[4] + '/' + urlSplit[5] + '/master/configurations/' + configName);
        const body = await response.text();
        localStorage.setItem("packageConfigYAML", body)
        // Once the fetch call is achieved naviagte to the editor page.
        window.location.assign("/generatePackage")
    }

    getCommunityConfigURL() {
        // Use the default GITHUB_API_URL
        let GITHUB_API_URL = "https://api.github.com/repos/sladyn98/custom-distribution-service-community-configurations/contents/configurations"
        // If environment variable has been set it will override the default
        if (process.env.REACT_APP_GITHUB_COMMUNITY_URL) {
            console.log("Environment variable has been set")
            GITHUB_API_URL = process.env.REACT_APP_GITHUB_COMMUNITY_URL
        }
        return GITHUB_API_URL
    }

    render() {
        return(
            <div>
                <Card className = "configurationCard" body inverse style={{ backgroundColor: '#001627', borderColor: '#333' }} >
                    <CardBody>
                    <CardTitle>Configuration Name: {this.props.config.name}</CardTitle>
                    <div className="card-footer text-center" style = {{marginTop:"10px"}}>
                    <Button onClick = {() => {this.viewDetails(this.props.config.name)}} style = {{backgroundColor:this.state.backgroundColor}} > {this.state.buttonText} </Button>
                    </div>
                    </CardBody>
                </Card>
            </div>
        )
    }
}

export default configurationCard;
