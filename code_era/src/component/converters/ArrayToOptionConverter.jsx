import React from 'react'

import { Data, Fullstack, Backend, Frontend, Others } from '../../data_categories.js'; 

class ArrayToOptionConverter extends React.Component{
	
	constructor(props) {
		super(props) 
		this.state = {
			Data: Data, 
			Fullstack: Fullstack, 
			Backend: Backend, 
			Frontend: Frontend, 
			Others: Others
		}
	}
	
	generateOptions(arr) {
		return arr.map((c, index) => {
				if(this.props.selected.includes(c.label)) {
					return <option key={index} value={c.value} selected>{c.label}</option>;
				}
              return <option key={index} value={c.value}>{c.label}</option>;
        })
	}
	
	render() {
		return <select id="category" multiple name="category" 
					onChange={(event) => {this.props.onInputChange("category", event);}}> 
					<optgroup label="DATA">
						{this.generateOptions(this.state.Data)}
					</optgroup>
					<optgroup label="FULLSTACK">
						{this.generateOptions(this.state.Fullstack)}
					</optgroup>
					<optgroup label="BACKEND">
						{this.generateOptions(this.state.Backend)}
					</optgroup>
					<optgroup label="FRONTEND">
						{this.generateOptions(this.state.Frontend)}
					</optgroup>
					<optgroup label="OTHERS">
						{this.generateOptions(this.state.Others)}
					</optgroup><div>{this.props.children} </div>
				</select>;
	}
}

export default ArrayToOptionConverter 