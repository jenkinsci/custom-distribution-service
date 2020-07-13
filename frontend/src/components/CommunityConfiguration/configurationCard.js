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

    render() {
        return(
            <div>
                <Card className = "configurationCard" body inverse style={{ backgroundColor: '#001627', borderColor: '#333' }} >
                    <CardBody>
                    <CardTitle>Configuration Name: {this.props.config.name}</CardTitle>
                    <div className="card-footer text-center" style = {{marginTop:"10px"}}>
                    <Button style = {{backgroundColor:this.state.backgroundColor}} > {this.state.buttonText} </Button>
                    </div>
                    </CardBody>
                </Card>
            </div>
        )
    }
}

export default configurationCard;