import React from 'react'
import Editor from 'react-simple-code-editor';
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-javascript';
import './Editor.scss';
import {
  Card, CardImg, CardText, CardBody,
  CardTitle, CardSubtitle, Button
} from 'reactstrap';

const code = `a {
    margin:10px;
}`;

class editor extends React.Component {

   state = { 
     code,
     title: '',
     description: '',
     }
   componentDidMount() {
    this.setState({code: localStorage.getItem("packageConfigYAML")})
    this.setState({title: JSON.parse(localStorage.getItem("packageConfigJSON"))["bundle"]["title"]})
    this.setState({description: JSON.parse(localStorage.getItem("packageConfigJSON"))["bundle"]["desc"]})
   }

   render()  {
    const packageJSON = JSON.parse(localStorage.getItem("packageConfigJSON"))
    console.log(packageJSON["bundle"]["desc"])

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
      <Button style = {{backgroundColor:"#185ecc", fontSize:"25px", margin:"10px"}} >Download Packager Config </Button>
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