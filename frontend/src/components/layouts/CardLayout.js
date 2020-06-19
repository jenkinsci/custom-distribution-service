import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col , Button} from 'reactstrap';


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
                pluginName: "gitlab Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "anchore Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "warnings Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "slack Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "kubernetes Plugin",
                artifactId: "github-api-plugin",
                version: "2.3",
                description: "Useful Github plugin"
            },
            {
                pluginName: "docker api Plugin",
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

        let pluginCards = this.state.plugins.map(plugin => {
            return(
                <Col sm="3">
                    <PluginCard plugin = {plugin} setClick={click => this.clickChild = click} />
                </Col>
            )
        })
        return (
            <Container fluid style = {{height: "100vh"}}>
                <Row>
                   {pluginCards} 
                </Row>
                <div className="card-footer text-center" >
                <Button style = {{backgroundColor:"#185ecc"}}  onClick={() => this.clickChild()}>Submit Plugins</Button>
                </div>
            </Container>
        )
    }
}

export default CardLayout;
