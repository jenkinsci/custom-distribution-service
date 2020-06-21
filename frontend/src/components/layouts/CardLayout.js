import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col} from 'reactstrap';
import { InputGroup, InputGroupAddon, Button, Input } from 'reactstrap';
import ModalExample from '../modal';


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
        search:"",
        modalShow : false
    }


    onchange = pluginName => {
        this.setState({search: pluginName.target.value})
    }

    changeModalState = () => {
        console.log(this.state.modalShow)
        this.setState({modalShow: !this.state.modalShow})
    }

    

    render () {

        const  search  = this.state.search
        let pluginCards = this.state.plugins.map(plugin => {
            
            if (search !== "" && plugin.pluginName.toLowerCase().indexOf( search.toLowerCase() ) === -1) {
                return null
            }
            return(
                <Col sm="3">
                    <PluginCard plugin = {plugin} setClick={click => this.clickChild = click} />
                </Col>
            )
        })

        return (
            <Container fluid style = {{height: "100vh"}}>
            <div
            style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center"
             }} >
                <InputGroup style={{margin:"15px", width:"50%"}}>
                <Input onChange = {this.onchange} />
                <InputGroupAddon addonType="append">
                <Button  style = {{backgroundColor:"#185ecc"}} >Search Plugin</Button>
                </InputGroupAddon>
                </InputGroup>
            </div>
                <Row>
                   {pluginCards} 
                </Row>
                <div className="card-footer text-center" >
                <Button style = {{backgroundColor:"#185ecc"}}  onClick={() => {this.clickChild(); this.changeModalState()}}>Submit Plugins</Button>
                </div>
                <ModalExample  modalState = {this.state.modalShow} setClick={click => this.clickChild = click} />
            </Container>
        )
    }
}

export default CardLayout;
