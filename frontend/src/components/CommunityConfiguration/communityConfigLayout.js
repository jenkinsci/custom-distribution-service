import React from 'react'
import PluginCard from '../PluginCards/pluginCard'
import { Container, Row, Col, Button} from 'reactstrap';
import { Pagination, PaginationItem, PaginationLink, Spinner } from 'reactstrap';
import ConfigurationCard from './configurationCard';

class communityConfigLayout extends React.Component {

    state = {
        search:"",
        data: [],
        isLoading: true
    }

    async componentDidMount() {
        const response = await fetch(process.env.REACT_APP_GITHUB_COMMUNITY_URL);
        const body = await response.json();
        this.setState({data: body})
    }

    render() {
        let configurationCards;
        configurationCards = this.state.data.map(config => {
            return(
                <Col sm="3">
                    <ConfigurationCard config = {config} /> 
                </Col>
            )
        }) 

        return(
            <Container fluid style = {{height: "100vh"}}>
            <Row>
                {configurationCards} 
             </Row>
            </Container> 
        )

    }
}

export default communityConfigLayout;
