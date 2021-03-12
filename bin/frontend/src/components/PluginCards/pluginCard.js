import React, { useState } from 'react'
import PropTypes from 'prop-types'
import {
    Card, CardBody,
    CardTitle, CardSubtitle, Button
} from 'reactstrap'
import './pluginCard.scss'

const PluginCard = ({ config, setConfiguration, name, version, callbackFromParent }) => {
    const [isPluginSelected, setIsPluginSelected] = useState(false)

    const addToConfiguration = () => {
        if (!config.plugins) {
            config.plugins = new Object();
        }
        
        if (name in config.plugins) {            
            setIsPluginSelected(!isPluginSelected);
            delete config.plugins[name];
        }
        else {
            config.plugins[name] = [version];
            setIsPluginSelected(!isPluginSelected);
        }

        setConfiguration(config)
    }

    return (
        <div>
            <Card className="pluginCard" body inverse >
                <CardBody>
                    <CardTitle>Plugin Name: {name}</CardTitle>
                    <CardSubtitle>Version: {version}</CardSubtitle>
                    <div className="card-footer text-center">
                        { (config.plugins && config.plugins[name])? <Button style={{backgroundColor: "#C34444"}} onClick={ addToConfiguration }>Remove from configuration</Button> :
                                                                    <Button onClick={ addToConfiguration }>Add to configuration</Button>}
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