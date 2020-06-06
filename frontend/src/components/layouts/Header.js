import React from 'react'

export const Header = () => {
    return(
        <header className="header colored sticky-header" data-scroll="out">
				<div className="c-container">
					<div className="nav-items">
					<a href="">
					<img className="site-logo" src="https://i.imgur.com/kAZS0eh.png" alt=""></img>
					</a>
					<ul className="main-nav">
						<li className="nav-item active">
						<a className="nav-link active" href="#">Home</a>
						</li>
						<li className="nav-item">
						<a className="nav-link" href="#">Plugins</a>
						</li>
						<li className="nav-item">
							<a className="nav-link" href="#">Community Config</a>
							</li>
							<li className="nav-item">
								<a className="nav-link" href="#">Package Generation</a>
							</li>
							<li className="nav-item">
								<a className="nav-link" href="#">War Download</a>
							</li>
					</ul>
					</div>
				</div>
			</header>
    )
}