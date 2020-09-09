import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Form } from 'reactstrap';
import {  FormGroup, Label, Input, Spinner } from 'reactstrap';

class ModalExample extends React.Component {
  
    state = {
        modalState: false,
        casc: false,
        title: '',
        warVersion: '', 
        artifactID: '',
        description: '',
        dockertag:'',
        dockerBase:'',
        isLoading: false
    }

    constructor(props) {
        super(props);
        this.toggle = this.toggle.bind(this);
    }

    componentDidMount() {
        this.setState({modalState: this.props.modalState})
        this.props.setClick(this.toggle);
     }

    toggle = () => {
        this.setState({modalState: !this.state.modalState})
    }

    generateJSON = () => {
        console.log("Generating JSON")
        var packagerInfo = {}
        // Create a bundle object
        var bundle = new Object()
        bundle["artifactId"] = this.state.artifactID
        bundle["title"] = this.state.title
        bundle["description"] = this.state.description
        packagerInfo["bundle"] = bundle
        // Add war object
        packagerInfo["war"] = {jenkinsVersion: this.state.warVersion}
        // Make the casc section permanently false since no support for it
        // https://github.com/jenkinsci/custom-distribution-service/issues/117 (Support Ticket)
        packagerInfo["casc"] = false
        // Add plugin object
        packagerInfo["plugins"] = JSON.parse(localStorage.getItem("pluginsArray"))
        // Add buildSettings object
        var docker = {}
        // docker["build"] = true
        docker["tag"] = this.state.dockertag
        docker["base"] = this.state.dockerBase
        packagerInfo["buildSettings"] = docker
        // Add default System Settings
        var sysSettings = {}
        sysSettings["setupWizard"] = "true"
        sysSettings["slaveAgentPort"] = "5000"
        sysSettings["slaveAgentPortEnforce"] = "true"
        packagerInfo["sysSettings"] = sysSettings
        localStorage.setItem("packageConfigJSON", JSON.stringify(packagerInfo))
        this.submitConfiguration();
    }

    submitConfiguration = async () => {
        console.log("Submitting configuration")
        this.setState({isLoading:true})

        // Use the default API_URL
        let API_URL = "http://localhost:8080/"

        // If environment variable has been set it will override the default
        if (process.env.REACT_APP_API_URL) {
            console.log("Environment variable has been set")
            API_URL = process.env.REACT_APP_API_URL
        }
        
        const apiURL = API_URL + "package/getPackageConfiguration";
        fetch(apiURL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: localStorage.getItem("packageConfigJSON"),
        })
            .then(response => response.text())
            .then(data => {
                console.log('Success:', data);
                // Store the configuration client side
                localStorage.setItem("packageConfigYAML", data)
                // Once the fetch call is achieved naviagte to the editor page.
                window.location.assign("/generatePackage")
            })
            .catch((error) => {
                console.error('Error:', error);
        });
    }

    handleChange = async (event) => {
        const { target } = event;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const { name } = target;
        await this.setState({
          [ name ]: value,
        });
      }

    render() {
        return (
            <div>
            <Modal isOpen= {this.state.modalState} >
                <ModalHeader >Packager Configuration</ModalHeader>
                <ModalBody>
                    <Form>
                    <FormGroup>
                        <Label for="title">Title</Label>
                        <Input type="text"
                         name = "title" 
                         id = "title" 
                         value = { this.state.title } 
                         placeholder = "Enter the title for the package eg: Jenkins WAR - all latest" 
                         onChange={ (e) => this.handleChange(e) }/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="warVersion">War Version</Label>
                        <Input type="text" name="warVersion" id="warVersion" value = { this.state.warVersion } 
                        onChange={ (e) => this.handleChange(e) }
                        placeholder="Enter the versinon for the war eg: 2.107.3" />
                    </FormGroup>
                    <FormGroup>
                        <Label for="artifactId">ArtifactID</Label>
                        <Input type="text" name="artifactID" id="artifactID" 
                        value = { this.state.artifactID }
                        onChange={ (e) => this.handleChange(e) }
                        placeholder="Enter the artifactID for the package eg: jenkins-all-latest" />
                    </FormGroup>   
                    <FormGroup>
                    <Label for="artifactId">Description</Label>
                        <Input type="textarea" name="description" id="description" 
                        value = { this.state.description }
                        onChange={ (e) => this.handleChange(e) }
                     />
                    </FormGroup>  
                    {/* Disable this section for now since we do 
                    not have support for JCaSC 
                    <FormGroup check>
                        <Label check>
                        <Input 
                         type="checkbox"
                         value = {this.state.casc}
                         onChange={ (e) => this.handleChange(e) }/>{' '}
                        Include Configuration as Code Section
                        </Label>
                    </FormGroup>    */}
                    <FormGroup>
                    <legend>Build Settings</legend>
                    <Label for = "dockertag">Docker Build</Label>
                        <Input type = "text" name = "dockertag" id = "dockertag" 
                        value = { this.state.dockertag }
                        onChange = { (e) => this.handleChange(e) }
                        placeholder = "Enter the docker tag you would like to build eg: jenkins-experimental/custom-war-packager-casc-demo"
                     />
                    </FormGroup> 

                    <FormGroup>
                        <Label for = "dockerbase">Docker Base</Label>
                        <Input type = "text" name = "dockerBase" id = "dockerBase" 
                        value = { this.state.dockerBase }
                        onChange = { (e) => this.handleChange(e) }
                        placeholder = "Enter the docker base for the package eg: jenkins/jenkins:2.138.2" />
                    </FormGroup>   
          
                    </Form>
                </ModalBody>
                <ModalFooter>
                {this.state.isLoading && <Spinner style = {{width : "2rem", height: "2rem", color:"#011a30", marginRight:"5px"}}> </Spinner>}
                <Button style = {{backgroundColor:"#185ecc"}} onClick = { this.generateJSON }>
                Generate Package Configuration
                </Button>{' '}
                <Button outline color="danger"  onClick = { this.toggle } >Cancel</Button>
                </ModalFooter>
            </Modal>
            </div>
        )
    }
}

export default ModalExample;