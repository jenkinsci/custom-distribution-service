import React from 'react'
import './pluginCard.scss'
import {
    Card, CardBody,
    CardTitle, CardSubtitle, Button
  } from 'reactstrap';
  
let pluginArray = []
class PluginCard extends React.Component {

    state = {
        backgroundColor: "#185ecc",
        buttonText: "Add to configuration"
    }

    constructor(props) {
        super(props)
        this.submitConfiguration = this.submitConfiguration.bind(this);
    }

    componentDidMount() {
        this.props.setClick(this.submitConfiguration);
     }
    
    addToConfiguration() {
        this.setState({ backgroundColor: "red", buttonText: "Remove from configuration"})
        var version = new Object ();
        version["version"] = this.props.plugin.version;
        var pluginInfo = new Object();
        pluginInfo[this.props.plugin.pluginName] = version;
        console.log("Pushing into plugin Array")
        pluginArray.push(pluginInfo)
        localStorage.setItem("pluginsArray", JSON.stringify(pluginArray))
    }

    submitConfiguration() {
        console.log("Saving plugin array")
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
                    <Button onClick = {() => this.addToConfiguration()} style ={{backgroundColor:this.state.backgroundColor}}> {this.state.buttonText} </Button>
                    </div>
                    </CardBody>
                </Card>
            </div>
        )
    }
}

export default PluginCard;