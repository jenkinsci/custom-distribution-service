import React from 'react'
import PropTypes from 'prop-types'
import {
    Card, CardBody,
    CardTitle, CardSubtitle, Button
} from 'reactstrap'
import './pluginCard.scss'
  
const PluginCard = ({ config, setConfiguration, name, version }) => {
    const addToConfiguration = () => {
        if (!config.plugins) {
            config.plugins = []
        }
        config.plugins.push({
            [name]: {
                version: version
            }
        })
        setConfiguration(config)
    }

    return (
        <div>
            <Card className="pluginCard" body inverse >
                <CardBody>
                    <CardTitle>Plugin Name: {name}</CardTitle>
                    <CardSubtitle>Version: {version}</CardSubtitle>
                    <div className="card-footer text-center">
                        <Button onClick={ addToConfiguration }>Add to configuration</Button>
                    </div>
                </CardBody>
            </Card>
        </div>
    )
}

PluginCard.propTypes = {
    config: PropTypes.object.isRequired,
    name: PropTypes.string.isRequired,
    setConfiguration: PropTypes.func.isRequired,
    version: PropTypes.string.isRequired,
}

export default PluginCard