import React, { useState } from 'react'
import { Header } from './components/layouts/Header'
import Editor from './components/PackageGeneration/Editor'
import { BrowserRouter as Router, Route, Redirect, Switch } from 'react-router-dom'
import PluginsLayout from './components/layouts/PluginsLayout'
import CommunityConfigLayout from './components/CommunityConfiguration/communityConfigLayout'

function App() {
    const [config, setConfiguration] = useState({
        bundle: {
            description: 'No description specified',
            title: 'No title Specified',
        }
    })
    return (
        <Router>
            <div className="App">
                <header className="App-header">
                    <Header />
                    <h4 className="App-warnings">Warning: This service is currently in alpha. Kindly <a href="https://github.com/jenkinsci/custom-distribution-service/issues/new">Submit community feedback</a></h4>
                </header>
                <Switch>
                    {/* Redirect to plugin list until we have a good landing page */}
                    <Route exact path="/"><Redirect to="/pluginList" /></Route>
                    <Route path="/generatePackage"><Editor config={ config } setConfiguration={ setConfiguration } /></Route>
                    <Route path="/pluginList"><PluginsLayout  config={ config } setConfiguration={ setConfiguration } /></Route>
                    <Route path="/communityConfiguration"><CommunityConfigLayout config={ config } setConfiguration={ setConfiguration } /></Route>
                    {/*
                    If nothing else so the generic not found page
                    <Route component={GenericNotFound} />
                    */}
                </Switch>
            </div>
        </Router>
    )
}

export default App
