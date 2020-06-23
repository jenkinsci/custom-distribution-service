import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col } from 'reactstrap';
import { Pagination, PaginationItem, PaginationLink } from 'reactstrap';
import { InputGroup, InputGroupAddon, Button, Input } from 'reactstrap';

class CardLayout extends React.Component {

    state = {
        plugins: {},
        pluginsperPage : 12,
        current :1
    }

    async componentDidMount() {
        const response = await fetch('/api/plugin/getPluginList');
        const body = await response.json();
        const mainBody = body["plugins"]
        this.setState({ plugins: mainBody});
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
          
                <Pagination aria-label="Page navigation example" >
              {this.state.current !== 1 && <PaginationItem>
                  <PaginationLink first onClick={updatePageWrapper(0)} />
              </PaginationItem>}
              {this.state.current > 1 && <PaginationItem>
                  <PaginationLink previous onClick ={updatePageWrapper(this.state.current - 1)} />
              </PaginationItem>}
              {currentPlugins.map((key, index) => {
                  const isCurrent = this.state.current == index;
                  return (
                      <PaginationItem key={index} active={isCurrent}>
                          <PaginationLink onClick={updatePageWrapper(index)}>
                              {index}
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

            </Container>
        )
    }
}



export default CardLayout;
