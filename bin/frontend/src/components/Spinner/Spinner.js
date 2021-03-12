import React from 'react'
import {Spinner as BootstrapSpinner} from 'reactstrap'

export const Spinner = () => {
    return (
        <div className="SpinnerComponent">
            <div className="container h-100">
                <div className="row h-100 justify-content-center align-items-center">
                    <BootstrapSpinner color="light" />
                </div>
            </div>
        </div>
    )
}
export default Spinner