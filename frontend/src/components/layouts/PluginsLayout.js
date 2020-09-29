import React from 'react'
import {API_URL} from '../../config'
import PluginCard from '../PluginCards/pluginCard'

import PropTypes from 'prop-types'

import CardLayout from './CardLayout'

export const PluginsLayout = ({ config, setConfiguration }) => {
    return (
        <CardLayout
            type="Plugins"
            config={ config }
            setConfiguration={ setConfiguration }
            cardInstanceFunc={ ({data}) => <PluginCard setConfiguration={ setConfiguration } config={ config } name={ data.name } version={ data.version } /> }
            resultsPrefilter={ (data) => Object.values(data.plugins) }
            url={ `${API_URL}api/plugin/getPluginList` }
        />
    )
}

PluginsLayout.propTypes = {
    config: PropTypes.object.isRequired,
    setConfiguration: PropTypes.func.isRequired,
}

export default PluginsLayout