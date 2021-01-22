import React, { Component } from 'react'

import BugDataService from '../service/BugDataService.js'; 
import FixDataService from '../service/FixDataService.js'; 

import { PROGLANG } from '../data_progLang.js';

import BreadcrumbUI from './../component/others/BreadcrumbUI';
import CreateSolutionModal from './../component/modals/CreateSolutionModal';
import SelectCategoriesInput from '../component/converters/ArrayToOptionConverter';

import TagsInput from 'react-tagsinput'
import { Formik, Form, ErrorMessage } from 'formik';
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'; 
import { Label, Button, Icon, List, Modal } from "semantic-ui-react"

var _code_languages = ["plain","c","cs","cpp","html","xml","css","javascript","python","sql","php","perl","ruby","markdown","auto"];

class CreateItemView extends Component {

	constructor(props) {
        super(props)
		
			this.state = {
				userId: 1, 
	            id: this.props.match.params.id,
				title: "", 
				progLang: "", 
				category: [], 
	            tags: [], 
	            description: "", 
				fix: [], 
				newFixItem: [], 
				openAdd: false, 
				openEdit: false, 
				oldName: "", 
				totalBugs: [], 
				item: [], 
				condition: "EDIT"
      		 }
		this.titleChange = this.titleChange.bind(this);
		this.progLangChange = this.progLangChange.bind(this);
		this.categoryChange = this.categoryChange.bind(this);
		this.onEditorChange = this.onEditorChange.bind(this);
		this.onFixTitleChange = this.onFixTitleChange.bind(this); 
		this.onFixEditorChange = this.onFixEditorChange.bind(this); 
		this.onFixDelete = this.onFixDelete.bind(this);  
		this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    componentDidMount() {

        console.log(this.state.id)

        // If it is a new record then quit
        if (this.state.id == -1) {
            return
        }

		// Retreive existing record 
    	BugDataService.retrieveItem(this.state.id)
        .then(response => this.setState({
			title: response.data.title, 
			progLang: response.data.progammingLanguage, 
			category: response.data.category.split(", "), 
	        tags: response.data.tags.split(", "), 
            description: response.data.description,   
			fix: response.data.fix, 
			oldName: response.data.title, 
			id: response.data.id,  
			item: response.data
        }))

		FixDataService.retrieveList(this.state.id)
        .then(response => this.setState({
			fix: response.data, 
        }))
    }

	validate(values) {
        let errors = {}
        if (!values.description) {
            errors.description = 'Enter a Description'
			 console.log("description is " + values.description + ", return error")
        } 
		if (!values.title) {
            errors.description = 'Enter a Title'
			console.log("title is " + values.title + ", return error");
        }
		if (!this.state.progLang) {
            errors.description = 'Select a programming Language'
			console.log("programming language is " + this.state.progLang + ", return error");
        }
		if (!this.state.category) {
            errors.description = 'Select a category'
			console.log("category is " + this.state.category + ", return error");
        }
        return errors
    }

    onSubmit(values) {

        let newItem = {
            id: this.props.match.params.id, 
			userId: this.props.match.params.id, 
			title: values.title, 
			tags:  values.tags.join(", "), 
			description: this.state.description, 
			category: this.state.category.join(", "), 
			progammingLanguage: this.state.progLang 
        }

		
		console.log(newItem)
		console.log("ID :" + newItem.id + " USER_ID: " + newItem.userId + " TITLE: " + 
						newItem.title + " TAGS: " + newItem.tags + " DESC: " + newItem.description + 
						" PROGLANG: " + newItem.progammingLanguage + " CATEGORIES: " + newItem.category)

        if (this.state.id == -1) {
            BugDataService.createItem(newItem);
			this.state.fix.map((data, index) => {
				data.id = index
				FixDataService.createItem(data)
			})
			this.props.history.push(``);
        } else {
            BugDataService.updateItem(this.state.id, newItem).then(() => this.props.history.push(``))
			this.state.fix.map((data, index) => {
				data.id = index
				FixDataService.updateItem(data)
			})
			this.props.history.push(``);
        }
    }

	titleChange(event) {
    	this.setState({title: event.target.value});
  	}

	progLangChange(event) {
    	this.setState({value: event.target.value, progLang: event.target.value});
  	} 

	categoryChange(event) {
		let arr = Array.from(event.target.selectedOptions, option => option.value) 
    	this.setState({value: arr, category: arr});
  	}

	onEditorChange(data) {
		try { 
			this.setState({data: data, description: data});  
		} catch (ex) { }
	}
	
	onFixTitleChange(event) {
		let newItem = {
			id:  -1, 
			title: event.target.value, 
			solution: this.state.newFixItem.solution, 
			noOfTimesWorked: 0, 
			bug: this.state.item
		}
		this.setState({ newFixItem: newItem });   
	}
	
	onFixEditorChange(data) {
		try { 
			let newItem = {
				id: this.state.newFixItem.id, 
				title: this.state.newFixItem.title, 
				solution: data, 
				noOfTimesWorked: this.state.newFixItem.noOfTimesWorked, 
				bug: this.state.item
			}
			this.setState({newFixItem: newItem});  
		} catch (ex) { }
	}
	
	onFixDelete(index) {
		var arr = this.state.fix; 
		arr.splice(index + 1, 1);
		this.setState({"fix": arr.map((x) => ({
						  id: x.id, 
						  noOfTimesWorked: x.noOfTimesWorked, 
						  title: x.title, 
						  solution: x.solution, 
						  bug: this.state.item
				        }))		
					});		
		console.log("Fix no." + index + " deleted");
	}
	
	onFixModalClose(index) {
		var arr = this.state.fix.map((x) => x);
		
		if(index == -1) {
			let newItem = {
				id: this.state.fix.length + 1, 
				noOfTimesWorked: 0, 
				title: this.state.newFixItem.title, 
				solution: this.state.newFixItem.solution, 
				bug: this.state.item
			}; 	
			
			arr.push(newItem); 
		} else {
			arr[index] = this.state.newFixItem; 
			console.log(index + " item updated"); 
		}
		this.setState({fix: arr.map((x) => ({
			  bug: x.bug, 
			  id:  x.id, 
			  noOfTimesWorked: x.noOfTimesWorked, 
			  title: x.title, 
			  solution: x.solution
	        }))		
		}); 
		
		this.setState({newFixItem: []}) 
		if(index == -1) {
			this.setState({openAdd: false}) 
		} else {
			this.setState({openEdit: false})
		}
	}

    tagChange = tags => {
    	this.setState({ tags });
  	};

	tagChangeInput = tag => {
	    const validTag = tag
	      .replace(/([^\w-\s]+)|(^\s+)/g, "")
	      .replace(/\s+/g, "-");
		this.setState({ tag: validTag });
	};
	
	onAddModalOpen() { 
		console.log("Add modal open");
		var header = "Add Solution"
		var button = <button className="btn" type="button" id="addFixBtn" title="Add Solution"> 
					 <span><Icon name="add circle" size="big" /> </span> 
					</button>;
					
		return <Modal className="bg-light fixModal" size="small" closeIcon   
	    		 onClose={() => this.setState({openAdd: false})} onOpen={() => this.setState({openAdd: true})}
      			 open={this.state.openAdd} trigger={button}> 
				      <Modal.Header>{header}</Modal.Header>
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
						  onClick={() => {this.onFixModalClose(-1)}}
				          positive
				        />
				      </Modal.Actions>
	    		</Modal>;
	} 
	
	onEditModalOpen(index) { 
		console.log("Edit modal open");
		var header = "Edit Solution";
		var button = <button className="btn btn-light mt-auto mb-auto r-0" type="button" 
					title="Edit"
					onClick={() => {this.setState({newFixItem: this.state.fix[index]});}}>
					<Icon name="edit" color="black"/> </button>;
			
		return <Modal className="bg-light fixModal" size="small" closeIcon 
	    		 onClose={() => this.setState({openEdit: false})} onOpen={() => this.setState({openEdit: true})}
      			 open={this.state.openEdit} trigger={button}> 
				      <Modal.Header>{header}</Modal.Header>
				      <Modal.Content>			 
				          &emsp;<Label as='a' color='red' ribbon>Title</Label> <br/>
				          <input className="col-7" type="text" placeholder="e.g. Cx001: OutOfBoundsException" 
								onChange={this.onFixTitleChange} name="fixName" 
								defaultValue={this.state.newFixItem.title} /> 
						  <br/><br/>
						  &emsp;<Label as='a' color='red' ribbon>Description</Label> <br/>
						  <CKEditor
							id="ckeditor2" 
							data={this.state.newFixItem.solution}
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
						  onClick={() => {this.onFixModalClose(index)}}
				          positive
				        />
				      </Modal.Actions>
	    		</Modal>;
	} 
	
	backBtnOnClick() {
	    this.props.history.push(``)
	}

    render() {
		let { id, title, oldName, progLang, category, description, fix } = this.state
		const { tags, tag } = this.state; 
		const progLangOptions = PROGLANG; 

	    return (
	        <div>
				<BreadcrumbUI id={this.state.id} condition="EDIT" name={this.state.oldName} 
					history={this.props.history} />
                <Formik initialValues={{ id, title, progLang, tags, description, fix }} 
					onSubmit={this.onSubmit}
					validateOnChange={false} validateOnBlur={false}
				    validate={this.validate} enableReinitialize={true} > 
						{(props) => (
                            <Form className="row ml-5">
								<div className="col-7 bg-light pt-4 col shadow">
									<h3><Icon name="bug" />Bug Report</h3>
									<br/>
									<Label as='a' color='red' ribbon>Name of the Bug </Label> 
									&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
									&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<Label class="ml-5" as='a' color='red' ribbon>Programming Language </Label> <br/>
									<input className="col-7" type="text" placeholder="e.g. Cx001: OutOfBoundsException" 
										onChange={this.titleChange} name="title" defaultValue={oldName} />
									&emsp;&nbsp;
								    <select id="progLang"
					                    name="progLang"
					                    onChange={this.progLangChange}>
										{progLangOptions.map((lang, index) => {
											if(lang === progLang) {
												return <option selected>{lang}</option>;
											}
						                  return <option>{lang}</option>;
						                })}
									 </select>
									<br/><br/>
									<Label class="ml-5" as='a' color='red' ribbon>Category </Label> 
									* Press Ctrl to select multiple
									<br/>
								    <SelectCategoriesInput selected={category} 
										categoryChange={this.categoryChange} />
									<br/> <br/>
									<Label as='a' color='red' ribbon>Description</Label> <br/>
									<CKEditor
										id="ckeditor" 
										name="desc"
										data={description}
					                    editor={ClassicEditor}
					                    placeholder="<p>e.g. Cause, Steps to reproduce...</p>"
					                    onChange={(event, editor) => {
									              	const data = editor.getData();
									                this.onEditorChange(data);
									             }}
					                />
									<br/>
									<Label as='a' color='red' ribbon>Tags </Label>
									
                                    <TagsInput className="react-tagsinput"
										name="tags"
										list="tagSuggest"
								        value={tags}
								        onChange={this.tagChange}
								        onChangeInput={this.tagChangeInput}
								        validationRegex={this.REGEXP}
										inputValue={tag}
								        onlyUnique
								        addOnBlur
								        addOnPaste
								        pasteSplit={this.onPaste}/>
									<br/>
									<br/><br/><br/><br/>
							</div>
							<div className="col-5 bg-light shadow">	
								<br/>
								<h3 id="fixListTitle">
									<Icon name="lightbulb outline" />Solutions</h3>
								<List id="dragList">
									
						            {this.state.fix.map((data, index) => {
								         return <div className="fixDiv shadow" key={index}>
												{this.onEditModalOpen(index)}
												<button className="btn btn-light mt-auto mb-auto r-0" 
													title="Delete" 
													type="button" onClick={() => {this.onFixDelete(index);}}>
													<Icon name="trash alternate outline" color="black"/> 
												</button> 
												<h4 className="fixTitle truncate">
													<small className="text-muted">#{data.id}&nbsp;&nbsp;</small>{data.title}
												</h4> 
												<p/>
                                       			<div className="truncate solu text-muted bg-transparent p-0 m-0" 
													dangerouslySetInnerHTML={{__html: data.solution}} />
												<br/>
											</div>;
			                         })}
									{this.onAddModalOpen()}
						          </List>
								<Button.Group>
								<button className="col-6 btn" id="backBtn" 
									onClick={() => this.backBtnOnClick()}>&#x1F804;&nbsp; BACK</button> 
                        		<button className="col-6 btn" type="submit"><Icon name="save outline"/> SAVE</button>
								</Button.Group> 
								<div className="d-flex m-auto bg-light empty" />	
							</div>							
                        </Form>
                        )
                    }
                </Formik>
	        </div>
	    )
    }
}

export default CreateItemView