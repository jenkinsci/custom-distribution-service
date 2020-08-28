import React from 'react';
import { Header } from './components/layouts/Header'
import Editor from './components/PackageGeneration/Editor'
import { BrowserRouter as Router, Route } from "react-router-dom"
import CardLayout from './components/layouts/CardLayout'
import CommunityConfigLayout from './components/CommunityConfiguration/communityConfigLayout';



function App() {
  return (
    <Router>
      <div className="App">
        <header className="App-header">
        <Header />
        <h4 style={{color:"red", fontFamily: "verdana", textAlign: "center"}}>Warning: This service is currently in alpha. Kindly Submit community feedback <a style = {{color: "red"}} href="https://github.com/jenkinsci/custom-distribution-service/issues/new">here</a></h4>

        <Route path="/generatePackage"><Editor /></Route>
        <Route path="/pluginList"><CardLayout /></Route>
        <Route path="/communityConfiguration"><CommunityConfigLayout /></Route>
        </header>
      </div>
    </Router>
  );
}

export default App;
