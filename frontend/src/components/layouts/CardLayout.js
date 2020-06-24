import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col } from 'reactstrap';
import { Pagination, PaginationItem, PaginationLink } from 'reactstrap';
import { InputGroup, InputGroupAddon, Button, Input, Spinner } from 'reactstrap';

class CardLayout extends React.Component {

    state = {
        plugins: {},
        pluginsperPage : 10,
        current :1,
        isLoading: true
    }

    async componentDidMount() {
        const response = await fetch('/api/plugin/getPluginList');
        const body = await response.json();
        const mainBody = body["plugins"]
        this.setState({ plugins: mainBody, isLoading: false});
    }

    render () {

        // We need to populate the plugin Array
        let pluginArray = []
        Object.keys(this.state.plugins).map(key => {
            pluginArray.push(this.state.plugins[key])
        });

        // Get current plugins
        const indexOfLastPlugin =  this.state.current * this.state.pluginsperPage;
        const indexOfFirstPlugin =  indexOfLastPlugin - this.state.pluginsperPage;
        const currentPlugins = pluginArray.slice(indexOfFirstPlugin, indexOfLastPlugin)  

        // Create index array
        const indexArray = []
        // Calculate first index
        let lastIndex = indexOfFirstPlugin / 10 + 1;
        let firstIndex = 0;
        if (lastIndex >= 10 ) {
            firstIndex = lastIndex - 10;
        } else {
            lastIndex = 9;
        }
        let counter = 0;
        for (let index = firstIndex + 1; index < lastIndex + 2; index++) {
            indexArray[counter] = index 
            counter = counter + 1;
        }

        const updatePageWrapper = (num) => {
            return (e) => {
                e.preventDefault();
                this.setState({current: num});     
            };
        };

        let pluginCards = currentPlugins.map(plugin => {
            return(
                <Col sm="3">
                    <PluginCard plugin = {plugin} /> 
                </Col>
            )
        });

        if(this.state.isLoading) {
            return (
                <div style = { {
                    position: "fixed",
                    top: "50%", 
                    left: "50%",
                    }}>
                 <Spinner style = {{width : "6rem", height: "6rem", color:"#011a30"}}> </Spinner>
                </div>
            )
        }

        return (
            <Container fluid>
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
                <Button color="secondary">Search Plugin</Button>
                </InputGroupAddon>
                </InputGroup>

                {/* Pagination Handling (Separate this into a different component later*/}
                <Pagination aria-label="Page navigation example" style ={{margin:"10px"}}>
                {this.state.current !== 1 && <PaginationItem>
                    <PaginationLink first onClick={updatePageWrapper(0)} />
                </PaginationItem>}
                {this.state.current > 1 && <PaginationItem>
                    <PaginationLink previous onClick ={updatePageWrapper(this.state.current - 1)} />
                </PaginationItem>}
                {currentPlugins.map((key, index) => {
                    const isCurrent = this.state.current == indexArray[index];
                    return (
                        <PaginationItem key={indexArray[index]} active={isCurrent}>
                            <PaginationLink onClick={updatePageWrapper(indexArray[index])}>
                                {indexArray[index]}
                            </PaginationLink>
                        </PaginationItem>
                    );
                })}
                {this.state.current !== 100 && <PaginationItem>
                    <PaginationLink next onClick={updatePageWrapper(this.state.current + 1)} />
                </PaginationItem>}
                {this.state.current !== 100 && <PaginationItem>
                    <PaginationLink last onClick={updatePageWrapper(pluginArray.length - 1)}/>
                </PaginationItem>}
                </Pagination>
            </div>
            <Row>
                   {pluginCards} 
            </Row>
        </Container>
        )
    }
}

export default CardLayout;
