import React, { Component } from 'react'

import { Label, Button, Icon, Modal } from "semantic-ui-react" 
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'; 

/* TOP BAR & LOGO, ALWAYS CALLED IN App.js */
function CreateSolutionModal(props) {
	
	const [openAdd] = React.useState(false) 
	
	const header = "Add Solution" 
	
	const button = <button className="btn" type="button" id="addFixBtn"> 
						<Icon name="add circle"/> 
							<span>Add Solution</span>
						</button>
						
	// let fixList = this.props.state.fix 
	
	let newFixItem = []
	
	function CreateSolutionModal(props) {
		this.onFixTitleChange = this.onFixTitleChange.bind(this); 
		this.onFixEditorChange = this.onFixEditorChange.bind(this); 
	}
	
	function onFixTitleChange(event) {
		let newItem = {
			id:  -1, 
			title: event.target.value, 
			solution: newFixItem.solution, 
			noOfTimesWorked: 0, 
		}
		newFixItem = newItem;   
	}
	
	function onFixEditorChange(data) {
		try { 
			let newItem = {
				id: this.state.newFixItem.id, 
				title: this.state.newFixItem.title, 
				solution: data, 
				noOfTimesWorked: this.state.newFixItem.noOfTimesWorked, 
				bug: this.state.newFixItem.bug 
			}
			this.setState({newFixItem: newItem});  
		} catch (ex) { }
	}
	
    return (
			<Modal className="bg-light fixModal" size="small" closeIcon   
    		 onClose={() => this.props.setState({openAdd: false})} 
			 onOpen={() => this.props.setState({openAdd: true})}
  			 open={openAdd} trigger={button}> 
			      <Modal.Header>{header}</Modal.Header>
			      <Modal.Content>			 
			          &emsp;<Label as='a' color='red' ribbon>Title</Label> <br/>
			          <input className="col-7" type="text" placeholder="e.g. Cx001: OutOfBoundsException" 
							onChange={onFixTitleChange} name="fixName" /> 
					  <br/><br/>
					  &emsp;<Label as='a' color='red' ribbon>Description</Label> <br/>
					  <CKEditor
						id="ckeditor2" 
			            editor={ClassicEditor} 
						onChange={(event, editor) => {
					              	const data = editor.getData();
					                 onFixEditorChange(data);
					             }}
			            placeholder="<p>e.g. Cause, Steps to reproduce...</p>" />
					</Modal.Content>			      
					<Modal.Actions>
			        <Button
			          content="Save"
			          labelPosition='right'
			          icon='checkmark'
					  onClick={() => {this.props.onFixModalClose(-1)}}
			          positive
			        />
			      </Modal.Actions>
    		</Modal>
        )
}

export default CreateSolutionModal