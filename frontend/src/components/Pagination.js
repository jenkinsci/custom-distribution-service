import React from 'react'

export const Pagination = ({ pluginsPerPage, totalPlugins}) => {
    const pageNumbers = [];

    console.log("PluginsPerPage" + pluginsPerPage + " " + totalPlugins)
    for(let i=0; i<= Math.ceil(totalPlugins / pluginsPerPage); i++ ){
        pageNumbers.push(i);
    }

    return (
       <nav>
           <ul className="pagination">
                {pageNumbers.map(number => (
                    <li key={number} className="page-item">
                        <a href="!#" className="page-link">
                            {number}
                        </a>
                    </li>
                ))}
           </ul>
       </nav>
    )
}

export default Pagination