import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col } from 'reactstrap';


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
                pluginName: "github Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "github Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "github Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "github Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "github Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "github Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "github Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            }
        ]
    }
    render () {

        let pluginCards = this.state.people.map(plugin => {
            return(
                <Col sm="3">
                    <PluginCard plugin = {plugin} />
                </Col>
            )
        })
        return (
            <Container fluid>
                <Row>
                   {pluginCards} 
                </Row>
            </Container>
        )
    }
}

export default CardLayout;
