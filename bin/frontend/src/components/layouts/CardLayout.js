import React, {useState, useEffect} from 'react'
import PropTypes from 'prop-types'
import { safeLoad as yamlRead } from 'js-yaml'
import { Container, Row, Col} from 'reactstrap'
import {
    InputGroup,
    InputGroupAddon,
    Button,
    Input,
    Pagination,
    PaginationItem,
    PaginationLink,
} from 'reactstrap'
import { RadioGroup, Radio } from 'react-radio-group'
import querystring from 'querystring'

import Spinner from '../Spinner/Spinner'
import {GITHUB_COMMUNITY_REPO} from '../../config'
import './CardLayout.scss'
import { ModalManager } from 'react-dynamic-modal';
import MyModal from '../Modal/MyModal'

const DEFAULT_SORT_TYPES = [
    ['relevance', 'Relevance'],
    ['installed', 'Most installed'],
    ['trend', 'Trending'],
    ['title', 'Title'],
    ['updated', 'Release date']
]

const DEFAULT_CARDS_PER_PAGE = 12

const CardLayout = ({
    setConfiguration,
    sortTypes,
    cardsPerPage,
    url,
    cardInstanceFunc,
    resultsPrefilter,
    type,
    config
}) => {
    const [currentPage, setCurrentPage] = useState(0)
    const [modalShow, setModalShow] = useState(false)
    const [isLoading, setIsLoading] = useState(false)
    const [data, setData] = useState([])
    const [search, setSearch] = useState('')
    const [query, setQuery] = useState('')
    const [isPluginSelected, setIsPluginSelected] = useState(true)

    useEffect(() => {
        async function fetchData() {
            const yamlStr = await fetch(`${url}?${query}`).then((response) => {
                if (!response.ok) {
                    throw new Error('Something went wrong')
                }
                return response.text()
            })
            const yaml = resultsPrefilter(yamlRead(yamlStr))
            setData(yaml)
            setIsLoading(false)
        }
        setIsLoading(true)
        fetchData()
    }, [query, url, resultsPrefilter])

    const onSearchChange = event => {
        setSearch(event.target.value)
    }

    const changeModalState = () => {
        setModalShow(!modalShow)
    }

    const onConfigChange = (newConfig) => {
        config.plugins = newConfig;
        setConfiguration(config);
        setIsPluginSelected(!isPluginSelected);
    }

    // Get current Datas
    const indexOfFirstData = currentPage * cardsPerPage
    const indexOfLastData = indexOfFirstData + cardsPerPage
    const currentData = data.slice(indexOfFirstData, indexOfLastData)  

    // Create index array
    const indexArray = []
    // Calculate first index
    let lastIndex = indexOfFirstData / 12 + 1     

    let firstIndex = 0
    if (lastIndex >= 12 ) {
        firstIndex = lastIndex - 12
    } else {
        lastIndex = 11
    }

    let counter = 0
    for (let index = firstIndex + 1; index < lastIndex + 2; index++) {
        indexArray[counter] = index 
        counter = counter + 1
    }

    const updatePageWrapper = (num) => {
        return (e) => {
            e.preventDefault()
            setCurrentPage(num)
        }
    }

    if (isLoading) {
        return <Spinner />
    }

    const configurationCards = currentData.filter(config => {
        if (search) {
            return config.name.toLowerCase().includes(search.toLowerCase())
        }
        return true
    }).map(data => {
        return (
            <Col sm="4" key={ data.name }>
                {cardInstanceFunc({ data, setConfiguration })} 
            </Col>
        )
    })

    const openModal = () => {
        ModalManager.open(<MyModal text={ config.plugins } onRequestClose={ () => true } onConfigChange={ onConfigChange } />);
    }

    return (
        <Container fluid style={ {height: '100vh'} } className="column">
            <div
                style={ {
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center'
                } } >

                {/* Filter Buttons */}
                <RadioGroup name="sort" onChange={ async (key) =>{
                    let data = {
                        categories: '',
                        labels:'',
                        page: 1,
                        query: 'javascript',
                        sort: key
                    }
                    const {categories, labels, page, query, sort} = data
                    setQuery(querystring.stringify({
                        categories,
                        labels,
                        page,
                        q: query,
                        sort
                    }))
                } } >
                    {sortTypes.map(([key, label]) => (
                        <label key={ key } style={ {margin: '5px', color:'white'} }>
                            <Radio value={ key }/>
                            {` ${label}`}
                        </label>
                    ))}
                </RadioGroup>

                {/* Search Bar */}
                <InputGroup style={ {margin:'10px', width:'40%'} }>
                    <Input onChange={ onSearchChange } />
                    <InputGroupAddon addonType="append">
                        <Button>Search Data</Button>
                    </InputGroupAddon>
                </InputGroup>

                {/* Pagination Handling (Separate this into a different component later*/}
                <Pagination aria-label="Page navigation example" style={ {margin:'10px'} }>
                    {currentPage !== 1 && <PaginationItem>
                        <PaginationLink first onClick={ updatePageWrapper(0) } />
                    </PaginationItem>}
                    {currentPage > 1 && <PaginationItem>
                        <PaginationLink previous onClick={ updatePageWrapper(currentPage - 1) } />
                    </PaginationItem>}
                    {currentData.map((key, index) => {
                        const isCurrent = currentPage === indexArray[index]
                        return (
                            <PaginationItem key={ indexArray[index] } active={ isCurrent }>
                                <PaginationLink onClick={ updatePageWrapper(indexArray[index]) }>
                                    {indexArray[index]}
                                </PaginationLink>
                            </PaginationItem>
                        )
                    })}
                    {currentPage !== 100 && <PaginationItem>
                        <PaginationLink next onClick={ updatePageWrapper(currentPage + 1) } />
                    </PaginationItem>}
                    {currentPage !== 100 && <PaginationItem>
                        <PaginationLink last onClick={ updatePageWrapper(data.length - 1) }/>
                    </PaginationItem>}
                </Pagination>
            </div>
            <Row>
                {configurationCards} 
            </Row>

            <div className="card-footer text-center">
                <Button style={{marginRight: 20}} onClick={ openModal }>View Selected Plugins</Button>
                <Button onClick={ changeModalState }>Submit {type}</Button>
            </div>
        </Container> 
                
    )
}

CardLayout.defaultProps = {
    // cardInstanceFunc: (config) => <ConfigurationCard setConfiguration={ setConfiguration } name={ config.name } file={ config.file } />
    cardsPerPage: DEFAULT_CARDS_PER_PAGE,
    sortTypes: DEFAULT_SORT_TYPES,
    url: `https://raw.githubusercontent.com/${GITHUB_COMMUNITY_REPO}/master/configurations.yaml`,
}

CardLayout.propTypes = {
    cardInstanceFunc: PropTypes.func.isRequired,
    cardsPerPage: PropTypes.number,
    resultsPrefilter: PropTypes.func.isRequired,
    setConfiguration: PropTypes.func.isRequired,
    sortTypes: PropTypes.arrayOf(PropTypes.arrayOf(PropTypes.string.isRequired, PropTypes.string.isRequired)),
    type: PropTypes.string.isRequired,
    url: PropTypes.string,
}

export default CardLayout
