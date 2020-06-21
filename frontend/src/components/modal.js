import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Form } from 'reactstrap';
import {  FormGroup, Label, Input } from 'reactstrap';


class ModalExample extends React.Component {
  
    state = {
        modalState: false,
        title: '',
        warVersion: '', 
        artifactID: '',
        description: ''
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
        var packagerInfo = {}
        // Create a bundle object
        var bundle = new Object()
        bundle["artifactID"] = this.state.artifactID
        bundle["title"] = this.state.title
        bundle["desc"] = this.state.description
        packagerInfo["bundle"] = bundle
        packagerInfo["war"] = {version: this.state.warVersion}
        packagerInfo["casc"] = true
        packagerInfo["plugins"] = JSON.parse(localStorage.getItem("pluginsArray"))
        console.log(packagerInfo)
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
                    <FormGroup check>
                        <Label check>
                        <Input type="checkbox" />{' '}
                        Include Configuration as Code Section
                        </Label>
                    </FormGroup>              
                    </Form>
                </ModalBody>
                <ModalFooter>
                <Button style = {{backgroundColor:"#185ecc"}} onClick = { this.generateJSON } >Generate Package Configuration</Button>{' '}
                <Button outline color="danger"  onClick = { this.toggle } >Cancel</Button>
                </ModalFooter>
            </Modal>
            </div>
        )
    }
}

export default ModalExample;