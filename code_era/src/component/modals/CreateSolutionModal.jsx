import React from 'react'

import { Label, Button, Icon, Modal } from "semantic-ui-react" 
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'; 

let header = ""; 
let button = "";

/* Add/Edit Solution Window */
class CreateSolutionModal extends React.Component {
	
	constructor(props) {
		super(props) 
		
		this.state = {
			open: false, 
			newItem: this.props.newFixItem
		}
		
		this.assignValues = this.assignValues.bind(this); 
		this.onFixTitleChange = this.onFixTitleChange.bind(this); 
		this.onFixEditorChange = this.onFixEditorChange.bind(this); 
		
		this.assignValues();
	} 			
	
	assignValues() {
		if(this.props.purpose === "add") {
			this.header = "Add Solution";  
			this.button = <button className="btn" type="button" id="addFixBtn"> 
							<span><Icon name="add circle" size="large" /> </span> 
						 </button>;
			
		} else {
			this.header = "Edit Solution";  
			this.button =  <button className="btn btn-light mt-auto mb-auto r-0" type="button" title="Edit">
						 <Icon name="edit" color="black"/> </button>; 
		}
	}
	
	onFixTitleChange(event) {
		try { 
			let newItem = {
				id:  this.state.newItem.id, 
				title: event.target.value, 
				solution: this.state.newItem.solution, 
				noOfTimesWorked: 0, 
				bug: this.props.bug
			}
			this.setState({newItem: newItem}); 
		} catch (ex) { }
	}
	
	onFixEditorChange(data) {
		try { 
			let newItem = {
				id: this.state.newItem.id, 
				title: this.state.newItem.title, 
				solution: data, 
				noOfTimesWorked: this.state.newItem.noOfTimesWorked, 
				bug: this.props.bug
			}
			this.setState({newItem: newItem});  
		} catch (ex) { }
	}
	
	onFixModalClose(index) {
		var arr = this.props.fixList.map((x) => x);
		
		if(this.props.purpose === "add") {
			let newItem = {
				id: arr.length + 1, 
				noOfTimesWorked: 0, 
				title: this.state.newItem.title, 
				solution: this.state.newItem.solution, 
				bug: this.props.bug
			}; 	
			
			arr.push(newItem); 
			console.log(index + " Fix item created"); 
		} else {
			arr[index] = this.state.newItem; 
			console.log(index + " Fix item updated"); 
		}
		
		this.props.modifyFix(arr); 
		
		this.setState({open: false});
	}
	
    render() {
		this.assignValues();
			return (<Modal className="bg-light fixModal" size="small" closeIcon   
    		 onClose={() => this.setState({open: false})} 
			 onOpen={() => this.setState({open: true})}
  			 open={this.state.open} trigger={this.button}> 
			      <Modal.Header>{this.header}</Modal.Header>
			      <Modal.Content>			 
			          &emsp;<Label as='a' color='red' ribbon>Title</Label> <br/>
			          <input className="col-7" type="text" placeholder="e.g. Cx001: OutOfBoundsException" 
							onChange={this.onFixTitleChange} name="fixName" /> 
					  <br/><br/>
					  &emsp;<Label as='a' color='red' ribbon>Description</Label> <br/>
					  <CKEditor
						id="ckeditor2" 
			            editor={ClassicEditor} 
						onChange={(event, editor) => {
					              	const data = editor.getData();
					                 this.onFixEditorChange(data);
					             }}
			            placeholder="<p>e.g. Cause, Steps to reproduce...</p>" />
					</Modal.Content>			      
					<Modal.Actions>
			        <Button
			          content="Save"
			          labelPosition='right'
			          icon='checkmark'
					  onClick={() => {this.onFixModalClose(this.state.newItem.id)}}
			          positive
			        />
			      </Modal.Actions>
    		</Modal>
	)}
}

export default CreateSolutionModal