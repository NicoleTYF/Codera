import React, { Component } from 'react';
import './App.css';
import CoderaApp from './views/CoderaApp'; 
import 'react-tagsinput/react-tagsinput.css'  

class App extends Component {
  render() {
    return (
      <div className="container" style={{ background: "#faf7eb" }}>
      	<title>Codera</title>
        <CoderaApp />
      </div>
    );
  }
}

export default App;
