import React from 'react'
import './pluginCard.scss'
import {
    Card, CardBody,
    CardTitle, CardSubtitle, Button
  } from 'reactstrap';
  
let pluginArray = []
class PluginCard extends React.Component {

    constructor(props) {
        super(props)
        this.submitConfiguration = this.submitConfiguration.bind(this);
    }

    componentDidMount() {
        this.props.setClick(this.submitConfiguration);
     }
    
    addToConfiguration() {
        var version = new Object ();
        version["version"] = this.props.plugin.version;
        var pluginInfo = new Object();
        pluginInfo[this.props.plugin.pluginName] = version;
        pluginArray.push(pluginInfo)
    }

    submitConfiguration() {
        console.log(pluginArray)
        localStorage.setItem("pluginsArray", JSON.stringify(pluginArray))
    }

    render() {
        return(
            <div>
                <Card className = "pluginCard" body inverse style={{ backgroundColor: '#001627', borderColor: '#333' }} >
                    <CardBody>
                    <CardTitle>Plugin Name: {this.props.plugin.pluginName}</CardTitle>
                    <CardSubtitle>Artifact ID: {this.props.plugin.artifactId}</CardSubtitle>
                    <CardSubtitle>Version: {this.props.plugin.version}</CardSubtitle>
                    <div className="card-footer text-center" style = {{marginTop:"10px"}}>
                    <Button onClick = {() => this.addToConfiguration()} style ={{backgroundColor:"#185ecc"}}>Add to configuration </Button>
                    </div>
                    </CardBody>
                </Card>
            </div>
        )
    }
}

export default PluginCard;