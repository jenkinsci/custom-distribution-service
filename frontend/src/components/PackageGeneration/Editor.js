import React from 'react'
import PropTypes from 'prop-types'
import { safeDump as yamlDump, safeLoad as yamlRead } from 'js-yaml'
import ReactSimpleCodeEditor from 'react-simple-code-editor'
import { highlight, languages } from 'prismjs/components/prism-core'
import 'prismjs/components/prism-clike'
import 'prismjs/components/prism-javascript'
import {
    Card,
    CardText,
    CardBody,
    CardTitle,
    Button
} from 'reactstrap'
import './Editor.scss'
import {API_URL} from '../../config'

// Creates a href element and allows the blob that is passed as data to be saved to the local disk
function saveData(blob, fileName) {
    let a = document.createElement('a')
    document.body.appendChild(a)
    a.style = 'display: none'
    let url = window.URL.createObjectURL(blob)
    a.href = url
    a.download = fileName
    a.click()
    window.URL.revokeObjectURL(url)
}

const downloadWarfile = (config) => {
    let xhr = new XMLHttpRequest()
    xhr.open('POST', `${API_URL}api/package/downloadWarPackage`, true)
    xhr.responseType = 'blob'
    xhr.onload = function () {
        if (xhr.status !== 200) {
            alert('This is something wrong with your configuration file. Please fix and try again')
            return
        }
        saveData(this.response, 'jenkins.war')
     
    }
    xhr.send(yamlDump(config))
}

const downloadPackagerConfig = (config) => {
    let xhr = new XMLHttpRequest()
    xhr.open('POST', `${API_URL}api/package/downloadPackageConfiguration`, true)
    xhr.responseType = 'blob'
    xhr.onload = function () {
        if (xhr.status !== 200) {
            alert('This is something wrong with your configuration file. Please fix and try again')
            return
        }
        saveData(this.response, 'casc.yml')
    }
    xhr.send(yamlDump(config))
}

export const Editor = ({ config, setConfiguration }) => {
    return (
        <div className="Editor row">
            <ReactSimpleCodeEditor className="EditorEditor"
                value={ yamlDump(config) }
                onValueChange={ code => {
                    setConfiguration(yamlRead(code))
                } }
                highlight={ code => highlight(code, languages.js) }
                padding={ 10 }
            />
            <div className="column">
                <Button onClick={ () => downloadWarfile(config) }>Download War File </Button>
                <Button onClick={ () => downloadPackagerConfig(config) }>Download Packager Config </Button>
                <Card>
                    <CardBody>
                        <CardTitle>Packager Details</CardTitle>
                        <CardText> Title: {config.bundle.title}</CardText>
                        <CardText> Description : {config.bundle.description}</CardText>
                    </CardBody>
                </Card>
            </div>
        </div>
    )
}

Editor.propTypes = {
    setConfiguration: PropTypes.func.isRequired,
    config: PropTypes.object.isRequired
}

export default Editor