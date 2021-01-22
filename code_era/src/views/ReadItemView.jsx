import React, { Component } from 'react'
import BugDataService from '../service/BugDataService.js'; 
import FixDataService from '../service/FixDataService.js'; 

import TagLabels from './../component/others/TagLabels';
import Breadcrumb from './../component/others/BreadcrumbUI';

import { Label, Button, Icon, List, Modal, Segment } from "semantic-ui-react" 
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

const KeyCodes = {
  comma: 188,
  enter: 13,
};

class ReadItemView extends Component {

	constructor(props) {
        super(props)
		
			this.state = {
				userId: 1, 
	            id: this.props.match.params.id,
				name: "", 
				progLang: "", 
				category: [], 
	            tags: [], 
	            description: "", 
				fix: [], 
				item: "", 
				newFixName: "", 
				newFixDesc: "", 
				condition: "READ"
      		 }
		this.onFixTitleChange = this.onFixTitleChange.bind(this); 
		this.onFixEditorChange = this.onFixEditorChange.bind(this); 
		this.onFixDelete = this.onFixDelete.bind(this); 
		this.deleteBtnOnClick = this.deleteBtnOnClick.bind(this); 
		this.updateBtnOnClick = this.updateBtnOnClick.bind(this); 
    }

    componentDidMount() {
		// Retreive existing record 
    	BugDataService.retrieveItem(this.state.id)
        .then(response => this.setState({ 
			data: response.data, 
			name: response.data.title, 
			progLang: response.data.progammingLanguage, 
			category: response.data.category.split(", "), 
	        tags: response.data.tags.split(", "), 
            description: response.data.description,   
			fix: response.data.fix, 
        }))
    }

	deleteBtnOnClick(id) {
    	BugDataService.deleteItem(id)
        .then(
            response => {
                this.setState({ message: `Delete item ${id} Successful` })
                this.props.history.push(``)
            }
        )
	}
	
	updateBtnOnClick(id) {
    	console.log('update ' + id)
   	    this.props.history.push(`/bugs/${id}`)
	} 
	
	onFixTitleChange(event) {
		let newItem = {
			title: event.target.value, 
			solution: this.state.newFixItem.solution
		}
		this.setState({newFixItem: newItem});  
	}
	
	onFixEditorChange(data) {
		try { 
			let newItem = {
				title: this.state.newFixItem.title, 
				solution: data 
			}
			this.setState({newFixItem: newItem});  
		} catch (ex) { }
	}

	onEditModalOpen(hasIndex, index) {
		let titleValue = "";
		let descValue = ""; 
		let ntw = 0;
		let button = <button class="btn" type="button" id="addFixBtn"> 
					<Icon name="add circle" color="white"/> 
						<span>Add Solution</span>
					</button>;
		if(hasIndex == true) {
			titleValue = this.state.fix[index].title; 
			descValue = this.state.fix[index].solution; 
			ntw = this.state.fix[index].noOfTimesWorked; 
			button = <button class="btn btn-light t-auto mb-auto r-0 float-right" type="button">
						<Icon name="edit outline" color="black"/> </button>;
		}
		return <Modal className="bg-light fixModal" size="small" closeIcon  
	      trigger={button}> 
				      <Modal.Header>Edit Solution</Modal.Header>
				      <Modal.Content>			 
				        <Modal.Description>
				          &emsp;<Label as='a' color='red' ribbon>Title</Label> <br/>
				          <input class="col-7" type="text" placeholder="e.g. Cx001: OutOfBoundsException" 
								onChange={this.ontitleChange} name="fixName" defaultValue={titleValue} /> 
						  <br/><br/>
						  &emsp;<Label as='a' color='red' ribbon>Description</Label> <br/>
						  <CKEditor
							id="ckeditor2" 
							data={descValue}
				            editor={ClassicEditor} 
							onChange={(event, editor) => {
						              	const data = editor.getData();
						                this.onFixEditorChange(data);
						             }}
				            placeholder="<p>e.g. Cause, Steps to reproduce...</p>" />
	
				        </Modal.Description>			      
						<Modal.Actions>
				        <Button
				          content="Save"
				          labelPosition='right'
				          icon='checkmark'
						  onClick={() => {
							let updateItem = {noOfTimesWorked: ntw, title: 
											this.state.newFixName, solution: 
											this.state.newFixDesc}; 	
							
							if(hasIndex == true) {
								console.log(index + " item updating");
								this.state.fix[index].noOfTimesWorked = ntw; 
								this.state.fix[index].title = this.state.newFixName; 
								this.state.fix[index].solution = this.state.newFixDesc; 
							} else {
								FixDataService.createItem(index, updateItem); 
								console.log(index + " new fix added");
							}
							
							FixDataService.updateItem(index, this.state.fix).then(
					            response => {
					                this.refreshList()
					            }
					        )
							
							console.log(this.state.fix)}}
				          positive
				        />
				      </Modal.Actions>
				      </Modal.Content>
	    </Modal>;
	} 
	
	onFixDelete(id) {
    	FixDataService.deleteItem(id)
        .then(
            response => {
                this.setState({ message: `Delete item ${id} Successful` })
                this.refreshList()
            }
        )
	}
	
	backBtnOnClick() {
	    this.props.history.push(``)
	}

    render() {
		let { id, name, progLang, category, description, fix } = this.state
		const { tags, tag } = this.state; 

	    return (
	        <div class="container">
				<Breadcrumb id={this.state.id} condition="READ" name={this.state.name} 
					history={this.props.history} /> 
				<br/>
				<div class="row d-flex pl-2 ml-3 mt-2" id="categoryText"> 
					<p id="progLangtext">{progLang} | {category.map((c, index) => {
		                  return c + " • ";
		                })}</p>  
				</div>
				  
				<div class=" pl-3 ml-3">
					<h3>{name}</h3>
					
					<div class="d-flex actionTools ml-auto">
						<button class="ui icon left labeled l-5 teal ui button attached left "
							onClick={() => this.updateBtnOnClick(id)}>
							<Icon name="edit" color="white" /> Edit </button>
						<button class="ui icon left labeled m-0 mr-4 red ui button attached right " 
							onClick={() => this.deleteBtnOnClick(id)}>
							<Icon name="trash alternate outline" color="white" /> Delete </button>
					</div>
					
           			<div class="bg-white p-3 mr-4 shadow" 
						dangerouslySetInnerHTML={{__html: description}} />
					<br/>
					 <div class="float-right mr-4" id="tagList">
						{this.state.tags.map((tag, index) => {
		                  return <Label as='a' tag color="teal" class="p-4 ml-2">
							        {tag} </Label>;
		                })}
					</div>
				
				
			<br/><br/> <hr/>
			<h3><Icon name="lightbulb outline" /> {fix.length}
						&nbsp; Solutions &emsp;&nbsp;</h3>
	        
			<List id="viewList">					
	            {this.state.fix.map((data, index) => {
			         return <div class="shadow" key={index}>
							{this.onEditModalOpen(true, index)}
							<button class="btn btn-light t-auto mb-auto r-0 float-right" type="button" 
								onClick={(index) => {this.onFixDelete(index);}}>
								<Icon name="trash alternate outline" color="black"/> </button>
							#{index} • <h4>{data.title}</h4> 
							<p/>
                   			<div class="text-muted bg-transparent p-0 m-0" 
								dangerouslySetInnerHTML={{__html: data.solution}} />
							<br/>
						</div>;
                 })}
	         </List>
			<br/> 
			<br/>
		</div></div>
	    )
    }
}

export default ReadItemView