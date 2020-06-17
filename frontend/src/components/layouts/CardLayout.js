import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col } from 'reactstrap';
import Pagination from '../Pagination';


class CardLayout extends React.Component {

    state = {
        plugins: {},
        currentPage : 1,
        pluginsperPage : 12
    }

    async componentDidMount() {
        const response = await fetch('/api/plugin/getPluginList');
        const body = await response.json();
        const mainBody = body["plugins"]
        console.log(countObjectKeys(mainBody))
        this.setState({ plugins: mainBody});
    }

    render () {


        let pluginArray = []
        Object.keys(this.state.plugins).map(key => {
            pluginArray.push(this.state.plugins[key])
        });
        
        // Get current posts
        const indexOfLastPlugin =  this.state.currentPage * this.state.pluginsperPage;
        const indexOfFirstPlugin =  indexOfLastPlugin - this.state.pluginsperPage;
        const currentPlugins = pluginArray.slice(indexOfFirstPlugin, indexOfLastPlugin)

        let pluginCards = currentPlugins.map(plugin => {
                return(
                    <Col sm="3">
                        <PluginCard plugin = {plugin} /> 
                    </Col>
                )
        });

        

        return (
            <Container fluid>
                <Row>
                   {pluginCards} 
                </Row>
                <Pagination pluginsPerPage={this.state.pluginsperPage} totalPlugins={pluginArray.length}/>
            </Container>
        )
    }
}

function countObjectKeys(obj) { 
    return Object.keys(obj).length; 
}


export default CardLayout;
