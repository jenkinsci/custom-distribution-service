import React from 'react'
import { Link, NavLink } from 'react-router-dom'
import { Navbar, Nav } from 'react-bootstrap'

export const Header = () => {
  return(
    <>
      <Navbar bg="#011a30" variant="dark" expand="lg">
        <Navbar.Brand as={Link} to="/"><img className="site-logo" src="https://www.jenkins.io/images/logos/jenkins/jenkins.svg" alt="" /></Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav>
            <Nav.Link as={NavLink} to="/pluginList">Plugins</Nav.Link>
            <Nav.Link as={NavLink} to="/generatePackage">Package Generation</Nav.Link>
            <Nav.Link as={NavLink} to="/communityConfiguration">Community Configurations</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </>
  )
}
