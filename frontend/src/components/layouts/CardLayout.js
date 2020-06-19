import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col } from 'reactstrap';
import { InputGroup, InputGroupAddon, Button, Input } from 'reactstrap';


class CardLayout extends React.Component {

    state = {
        plugins: [
            {
                pluginName: "github Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "USeful pLugin"
            },
            {
                pluginName: "gitlab plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "Kubernetes plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "java plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "Prometheus plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "Anchore",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "Warnings plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "slack plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            }
        ],
        search:""
    }


    onchange = pluginName => {
        this.setState({search: pluginName.target.value})
    }

    render () {
        const  search  = this.state.search
        let pluginCards = this.state.people.map(plugin => {
            
            if (search !== "" && plugin.pluginName.toLowerCase().indexOf( search.toLowerCase() ) === -1) {
                return null
            }
            return(
                <Col sm="3">
                    <PluginCard plugin = {plugin} />
                </Col>
            )
        })
        return (
            <Container fluid>
            <div
            style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center"
             }} >
                <InputGroup style={{margin:"10px", width:"50%"}}>
                <Input onChange = {this.onchange} />
                <InputGroupAddon addonType="append">
                <Button color="secondary">Search Plugin</Button>
                </InputGroupAddon>
                </InputGroup>
            </div>
                <Row>
                   {pluginCards} 
                </Row>
            </Container>
        )
    }
}

export default CardLayout;
