import React from 'react'
import { Link } from 'react-router-dom'

export const Header = () => {
    return(
        <header className="header colored sticky-header" data-scroll="out">
				<div className="c-container">
					<div className="nav-items">
					<a href="">
					<img className="site-logo" src="https://i.imgur.com/kAZS0eh.png" alt=""></img>
					</a>
					<ul className="main-nav">
					 <Link to="/pluginList">
						<li className="nav-item">
							<a className="nav-link">Plugins</a>
						</li>
					</Link>
					 <Link to="/generatePackage">
						<li className="nav-item">
							<a className="nav-link" >Package Generation</a>
						</li>
					</Link>
					</ul>
					</div>
				</div>
			</header>
    )
}