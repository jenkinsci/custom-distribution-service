import React from 'react'
import Editor from 'react-simple-code-editor';
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-javascript';
import './Editor.scss'

const code = `a {
    margin:10px;
}`;

class editor extends React.Component {

   state = { code };
   render() {return(
       <div className="Container">
        <Editor className="Editor"
        value={this.state.code}
        onValueChange={code => this.setState({ code })}
        highlight={code => highlight(code, languages.js)}
        padding={10}
        style={{
          fontFamily: '"Fira code", "Fira Mono", monospace',
          fontSize: 20,
        }}
      />
      </div>
    )
   }
}

export default editor;