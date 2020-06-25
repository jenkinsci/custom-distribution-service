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
                <Card className = "pluginCard" body inverse style={{ backgroundColor: '#001627', borderColor: '#333' }} >
                    <CardBody>
                    <CardTitle>Plugin Name: {this.props.plugin.name}</CardTitle>
                    <CardSubtitle>Version: {this.props.plugin.version}</CardSubtitle>
                    <CardText>Build Date: {this.props.plugin.buildDate}</CardText>
                    <Button style ={{backgroundColor:"#185ecc", marginBottom:"5px"}}>Add to configuration</Button>{' '}
                    </CardBody>
                </Card>
            </div>
        )
    }
}

export default PluginCard;