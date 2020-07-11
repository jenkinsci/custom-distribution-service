import React from 'react'
import Editor from 'react-simple-code-editor';
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-javascript';
import './Editor.scss';
import {
  Card, CardText, CardBody,
  CardTitle , Button
} from 'reactstrap';

const code = `a {
    margin:10px;
}`;

function saveData(blob, fileName) {
  var editor_config = document.createElement("editor-config");
  document.body.appendChild(a);
  editor_config.style = "display: none";
  var url = window.URL.createObjectURL(blob);
  editor_config.href = url;
  editor_config.download = fileName;
  editor_config.click();
  window.URL.revokeObjectURL(url);
}

class editor extends React.Component {

   state = { 
     code,
     title: '',
     description: '',
     isLoading: true
     }

   componentDidMount() {
    this.setState({code: localStorage.getItem("packageConfigYAML")})
    this.setState({title: JSON.parse(localStorage.getItem("packageConfigJSON"))["bundle"]["title"]})
    this.setState({description: JSON.parse(localStorage.getItem("packageConfigJSON"))["bundle"]["desc"]})
   }

   downloadPackagerConfig() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", 'http://localhost:8080/package/downloadPackageConfiguration', true);
    xhr.responseType = "blob";
    xhr.onload = function () {
        saveData(this.response, 'casc.yml');
    };
    xhr.send(localStorage.getItem("packageConfigYAML"));
   }

   render()  {
    const packageJSON = JSON.parse(localStorage.getItem("packageConfigJSON"))

    return(
       <div className="row" style = {{padding:"10px", borderRadius:"10px", margin:"0 auto"}}>
        <Editor className="Editor"
        value={this.state.code}
        onValueChange={code => this.setState({ code })}
        highlight={code => highlight(code, languages.js)}
        padding={10}
        style={{
          fontFamily: '"Fira code", "Fira Mono", monospace',
          fontSize: 20,
          color: "white"
        }}
      />
      <div className="column">
      <Button onClick = { this.downloadPackagerConfig }style = {{backgroundColor:"#185ecc", fontSize:"25px", margin:"10px"}} >Download Packager Config </Button>
      <Button style = {{backgroundColor:"#185ecc", fontSize:"25px"}} >Download War File </Button>
      <Card style = {{ margin:"10px"}}>
        <CardBody>
          <CardTitle>Packager Details</CardTitle>
          <CardText> Title: {this.state.title}</CardText>
          <CardText> Description : {this.state.description}</CardText>
        </CardBody>
      </Card>
      </div>
      </div>
    )

   }
}

export default editor;