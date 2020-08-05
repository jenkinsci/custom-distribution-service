import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col, Button} from 'reactstrap';
import ConfigurationCard from './configurationCard';
import { InputGroup, InputGroupAddon,Input, Label } from 'reactstrap';


class communityConfigLayout extends React.Component {

    state = {
        search:"",
        data: [],
        isLoading: true
    }

    async componentDidMount() {

        // Use the default GITHUB_API_URL
        let GITHUB_API_URL = "https://api.github.com/repos/sladyn98/custom-distribution-service-community-configurations/contents/configurations"

        // If environment variable has been set it will override the default
        if (process.env.REACT_APP_GITHUB_COMMUNITY_URL) {
            console.log("Environment variable has been set")
            GITHUB_API_URL = process.env.REACT_APP_GITHUB_COMMUNITY_URL
        }

        const response = await fetch(GITHUB_API_URL);
        const body = await response.json();
        this.setState({data: body})
    }
    
    onchange = pluginName => {
        console.log("Searching Plugins")
        this.setState({search: pluginName.target.value})
    }

    render() {
        const search  = this.state.search
        let configurationCards;
        if (search !== "") {
            configurationCards = this.state.data.map(config => {
                if(!(config["name"].toLowerCase().indexOf( search.toLowerCase() ) === -1)) {
                    return(
                    <Col sm="3">
                        <ConfigurationCard config = {config} /> 
                    </Col>
                    )
                }
            })    
        } else {
            configurationCards = this.state.data.map(config => {
                if (search !== "" && config["name"].toLowerCase().indexOf( search.toLowerCase() ) === -1) {
                    return null
                }
                return(
                    <Col sm="3">
                        <ConfigurationCard config = {config} /> 
                    </Col>
                )
            })  
        }   

        return(
            <Container fluid style = {{height: "100vh"}} class="column">
            {/* Div to center align the search box */}
            <div
                style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center"
                }} >


            {/* Search Bar */}
            <InputGroup style={{margin:"10px", width:"40%"}}>
            <Input onChange = {this.onchange} />
            <InputGroupAddon addonType="append">
            <Button  style = {{backgroundColor:"#185ecc"}} >Search Community Config</Button>
            </InputGroupAddon>
            </InputGroup>
            </div>
            <Row>
                {configurationCards} 
             </Row>
            </Container> 
        )

    }
}

export default communityConfigLayout;
