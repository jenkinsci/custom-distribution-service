import React from 'react';
import { Header } from './components/layouts/Header'
import Editor from './components/PackageGeneration/Editor'
import { BrowserRouter as Router, Route } from "react-router-dom"
import CardLayout from './components/layouts/CardLayout'



function App() {
  return (
    <Router>
      <div className="App">
        <header className="App-header">
        <Header />
        <Route path="/generatePackage"><Editor /></Route>
        <Route path="/pluginList"><CardLayout /></Route>
        </header>
      </div>
    </Router>
  );
}

export default App;
