import React from 'react'
import './pluginCard.scss'
import {
    Card, CardImg, CardText, CardBody,
    CardTitle, CardSubtitle, Button
  } from 'reactstrap';
  

class PluginCard extends React.Component {

    constructor(props) {
        super(props)
    }

    render() {
        return(
            <div>
                <Card className = "pluginCard" body inverse style={{ backgroundColor: '#778895', borderColor: '#333' }} >
                    <CardBody>
                    <CardTitle>Plugin Name: {this.props.plugin.pluginName}</CardTitle>
                    <CardSubtitle>Artifact ID: {this.props.plugin.artifactId}</CardSubtitle>
                    <CardSubtitle>Version: {this.props.plugin.version}</CardSubtitle>
                    <CardText>Description: {this.props.plugin.description}</CardText>
                    <Button style ={{backgroundColor:"#185ecc", marginBottom:"5px"}}>Add to configuration</Button>{' '}
                    <Button style ={{backgroundColor:"#185ecc"}}>Change Version Number</Button>{' '}
                    </CardBody>
                </Card>
            </div>
        )
    }
}

export default PluginCard;