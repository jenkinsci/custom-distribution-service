import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col} from 'reactstrap';
import { InputGroup, InputGroupAddon, Button, Input, Label } from 'reactstrap';
import ModalExample from '../modal';
import { Pagination, PaginationItem, PaginationLink, Spinner } from 'reactstrap';
import {RadioGroup, Radio} from 'react-radio-group';
import querystring from 'querystring';


class CardLayout extends React.Component {

   
    state = {
        search:"",
        modalShow : false,
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

    changeModalState = () => {
        console.log(this.state.modalShow)
        this.setState({modalShow: !this.state.modalShow})
    }

    onchange = pluginName => {
        this.setState({search: pluginName.target.value})
        console.log("Searching Plugins")
    }

    changeModalState = () => {
        console.log(this.state.modalShow)
        this.setState({modalShow: !this.state.modalShow})
    }

    render () {
         // We need to populate the plugin Array
         let pluginArray = []
         Object.keys(this.state.plugins).map(key => {
             pluginArray.push(this.state.plugins[key])
         });

        const search  = this.state.search
       
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

        let pluginCards;
        if (search !== "") {
            pluginCards = pluginArray.map(plugin => {
                if(!(plugin["name"].toLowerCase().indexOf( search.toLowerCase() ) === -1)) {
                    return(
                        <Col sm="3">
                            <PluginCard plugin = {plugin} /> 
                        </Col>
                    )
                }
            })    
        } else {
            pluginCards = currentPlugins.map(plugin => {
                if (search !== "" && plugin["name"].toLowerCase().indexOf( search.toLowerCase() ) === -1) {
                    return null
                }
                return(
                    <Col sm="3">
                        <PluginCard plugin = {plugin} /> 
                    </Col>
                )
            })  
        }   

        const updatePageWrapper = (num) => {
            return (e) => {
                e.preventDefault();
                this.setState({current: num});     
            };
        };

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

        const SORT_TYPES = [
            ['relevance', 'Relevance'],
            ['installed', 'Most installed'],
            ['trend', 'Trending'],
            ['title', 'Title'],
            ['updated', 'Release date']
        ];

        return (
            <Container fluid style = {{height: "100vh"}} class="column">
            <div
                style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center"
                }} >

                {/* Filter Buttons */}
                <RadioGroup name="sort" onChange = {async (key) =>{
                let data = {
                            categories: '',
                            labels:'',
                            page: 1,
                            query: 'javascript',
                            sort: key
                        }
                        const {categories, labels, page, query, sort} = data;
                        const params = querystring.stringify({
                            categories,
                            labels,
                            page,
                            q: query,
                            sort
                        });
                        console.log("Fetching values with key", key)
                        const url = `https://plugins.jenkins.io/api/plugins?${params}`
                        console.log("URL is ", url)
                        const response = await fetch(url);
                        const body = await response.json();
                        const mainBody = body["plugins"]
                        this.setState({plugins: mainBody, isLoading: false})
                } } >
                    {SORT_TYPES.map(([key, label]) => (
                        <label key={key} style = {{margin: "5px", color:"white"}}>
                            <Radio value={key}/>
                            {` ${label}`}
                        </label>
                    ))}
                </RadioGroup>

                {/* Search Bar */}
                <InputGroup style={{margin:"10px", width:"40%"}}>
                <Input onChange = {this.onchange} />
                <InputGroupAddon addonType="append">
                <Button  style = {{backgroundColor:"#185ecc"}} >Search Plugin</Button>
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
                <div className="card-footer text-center" >
                <Button style = {{backgroundColor:"#185ecc"}}  onClick={() => {this.clickChild(); this.changeModalState()}}>Submit Plugins</Button>
                </div>
                <ModalExample  modalState = {this.state.modalShow} setClick={click => this.clickChild = click} />
            </Container> 
                  
        )
    }
}

export default CardLayout;
