import React, {useState} from 'react'
import { Link, NavLink as RouterNavLink } from 'react-router-dom'
import {
    Collapse,
    Navbar,
    NavbarToggler,
    NavbarBrand,
    Nav,
    NavItem,
    NavLink
} from 'reactstrap'
export const Header = () => {
    const [isOpen, setIsOpen] = useState(false)

    const toggleNavbar = () => setIsOpen(!isOpen)

    return (
        <>
            <Navbar bg="#011a30" dark expand="lg">
                <NavbarBrand tag={ Link } to="/"><img className="site-logo" src="https://www.jenkins.io/images/logos/jenkins/jenkins.svg" alt="" /></NavbarBrand>
                <NavbarToggler aria-controls="basic-navbar-nav" onClick={ toggleNavbar } className="mr-2" />
                <Collapse isOpen={ isOpen } navbar>
                    <Nav className="mr-auto" navbar>
                        <NavItem>
                            <NavLink tag={ RouterNavLink } to="/pluginList">Plugins</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink tag={ RouterNavLink } to="/generatePackage">Package Generation</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink tag={ RouterNavLink } to="/communityConfiguration">Community Configurations</NavLink>
                        </NavItem>
                    </Nav>
                </Collapse>
            </Navbar>
        </>
    )
}
