import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col } from 'reactstrap';
import { Pagination, PaginationItem, PaginationLink } from 'reactstrap';



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
        console.log(countObjectKeys(mainBody))
        this.setState({ plugins: mainBody});
    }

    render () {

        let pluginArray = []
        Object.keys(this.state.plugins).map(key => {
            pluginArray.push(this.state.plugins[key])
        });

          // Get current posts
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
                <Row>
                   {pluginCards} 
                </Row>
          
                <Pagination aria-label="Page navigation example">
              {this.state.current !== 1 && <PaginationItem>
                  <PaginationLink first onClick={updatePageWrapper(1)} />
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
                  <PaginationLink last onClick={updatePageWrapper(pluginArray.length)}/>
              </PaginationItem>}
          </Pagination>

            </Container>
        )
    }
}

function countObjectKeys(obj) { 
    return Object.keys(obj).length; 
}


export default CardLayout;
