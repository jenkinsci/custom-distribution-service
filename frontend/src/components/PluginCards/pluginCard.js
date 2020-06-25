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
    }

    addToConfiguration() {
        var version = new Object ();
        version["version"] = this.props.plugin.version;
        var pluginInfo = new Object();
        pluginInfo[this.props.plugin.name] = version;
        console.log("Pushing into plugin Array" + pluginInfo[this.props.plugin.name])
        pluginArray.push(pluginInfo)
        localStorage.setItem("pluginsArray", JSON.stringify(pluginArray))
    }

    render() {
        return(
            <div>
                <Card className = "pluginCard" body inverse style={{ backgroundColor: '#001627', borderColor: '#333' }} >
                    <CardBody>
                    <CardTitle>Plugin Name: {this.props.plugin.name}</CardTitle>
                    <CardSubtitle>Version: {this.props.plugin.version}</CardSubtitle>
                    <div className="card-footer text-center" style = {{marginTop:"10px"}}>
                    <Button onClick = {() => this.addToConfiguration()} style = {{backgroundColor:this.state.backgroundColor}}> {this.state.buttonText} </Button>
                    </div>
                    </CardBody>
                </Card>
            </div>
        )
    }
}

export default PluginCard;