import React from 'react'
import PropTypes from 'prop-types'
import { useHistory } from 'react-router-dom'
import { safeLoad as yamlRead } from 'js-yaml'
import {
    Card,
    CardBody,
    CardTitle,
    Button
} from 'reactstrap'
import { GITHUB_COMMUNITY_REPO } from '../../config'


export const ConfigurationCard = ({ name, file, setConfiguration }) => {
    const history = useHistory()

    const viewDetails = async () => {
        const yamlStr = await fetch(
            `https://raw.githubusercontent.com/${GITHUB_COMMUNITY_REPO}/master/configurations/${file}`
        ).then((response) => {
            if (response.ok) {
                return response.text()
            } else {
                throw new Error('Something went wrong')
            }
        })
        if (!yamlStr) {
            alert(`${file} has no content`)
            return
        }
        const yaml = yamlRead(yamlStr)
        setConfiguration(yaml)
        // Once the fetch call is achieved naviagte to the editor page.
        history.push('/generatePackage')
    }

    return (
        <div>
            <Card className="configurationCard" body inverse >
                <CardBody>
                    <CardTitle>Configuration Name: {name}</CardTitle>
                    <div className="card-footer text-center">
                        <Button onClick={ viewDetails } >View details</Button>
                    </div>
                </CardBody>
            </Card>
        </div>
    )

}

ConfigurationCard.propTypes = {
    name: PropTypes.string.isRequired,
    file: PropTypes.string.isRequired,
    setConfiguration: PropTypes.func.isRequired
}

export default ConfigurationCard