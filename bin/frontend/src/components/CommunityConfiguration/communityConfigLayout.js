import React, {useState, useEffect} from 'react'
import PropTypes from 'prop-types'
import { Container, Row, Col, Button, Progress } from 'reactstrap'
import ConfigurationCard from './configurationCard'
import { InputGroup, InputGroupAddon, Input } from 'reactstrap'
import { safeLoad as yamlRead } from 'js-yaml'
import { GITHUB_COMMUNITY_REPO } from '../../config'


const CommunityConfigLayout= ({ setConfiguration }) => {
    const [isLoading, setIsLoading] = useState(false)
    const [data, setData] = useState([])
    const [search, setSearch] = useState('')

    useEffect(() => {
        async function fetchData() {
            const yamlStr = await fetch(
                `https://raw.githubusercontent.com/${GITHUB_COMMUNITY_REPO}/master/configurations.yaml`
            ).then((response) => {
                if (response.ok) {
                    return response.text()
                } else {
                    throw new Error('Something went wrong')
                }
            })
            setData(yamlRead(yamlStr))
            setIsLoading(false)
        }
        setIsLoading(true)
        fetchData()
    }, [])

    const onchange = event => {
        setSearch(event.target.value)
    }

    const configurationCards = data.filter(config => {
        if (search) {
            return config.name.toLowerCase().includes(search.toLowerCase())
        }
        return true
    }).map(config => {
        return (
            <Col sm="3" key={ config.name }>
                <ConfigurationCard setConfiguration={ setConfiguration } name={ config.name } file={ config.file } /> 
            </Col>
        )
    })

    return (
        <Container fluid style={ {height: '100vh'} } className="column">
            {/* Div to center align the search box */}
            <div
                style={ {
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center'
                } } >


                {/* Search Bar */}
                <InputGroup style={ {margin:'10px', width:'40%'} }>
                    <Input onChange={ onchange } />
                    <InputGroupAddon addonType="append">
                        <Button>Search Community Config</Button>
                    </InputGroupAddon>
                </InputGroup>
            </div>
            <Row>
                {isLoading && <Progress bar value={ 60 } />}
                {configurationCards} 
            </Row>
        </Container> 
    )
}

CommunityConfigLayout.propTypes = {
    setConfiguration: PropTypes.func.isRequired
}

export default CommunityConfigLayout
