import React from 'react'

export const Header = () => {
    return(
        <header class="header colored sticky-header" data-scroll="out">
				<div class="c-container">
					<div class="nav-items">
					<a href="">
					<img class="site-logo" src="frontend/src/images/jenkins_logo.png" alt=""></img>
					</a>
					<ul class="main-nav">
						<li class="nav-item active">
						<a class="nav-link active" href="#">Home</a>
						</li>
						<li class="nav-item">
						<a class="nav-link" href="#">Plugins</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Community Config</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="#">Package Generation</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="#">BLOGS</a>
							</li>
					</ul>
					</div>
				</div>
			</header>
    )
}