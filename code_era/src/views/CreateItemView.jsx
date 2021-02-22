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
import { Label, Button, Icon, List } from "semantic-ui-react"

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
				item: []
      		 }
		this.onInputChange = this.onInputChange.bind(this);
		this.onFixDelete = this.onFixDelete.bind(this); 
		this.modifyFix = this.modifyFix.bind(this); 
		this.onSubmit = this.onSubmit.bind(this);
        this.validate = this.validate.bind(this);
    }

    componentDidMount() {

        console.log(this.state.id)

        // If it is a new record then quit
        if (this.state.id == -1) {
            return
        }

		// Retreive existing record 
    	BugDataService.retrieveItem(this.state.id)
        .then(response => {
			var c = response.data.category; 
			var t = response.data.tags;
			if(response.data.category.length > 1) {
				c = response.data.category.split(", ")
			} 
			
			if(response.data.tags === null) {
				t = []
			} else if(response.data.tags.length > 1) {
				t = response.data.tags.split(", ")
			}

			this.setState({
				title: response.data.title, 
				progLang: response.data.progammingLanguage, 
				category: c, 
		        tags: t, 
	            description: response.data.description,   
				fix: response.data.fix, 
				id: response.data.id,  
				item: response.data
	        })
		})

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

		if(values.tags != null) {
			values.tags = values.tags.join(", ") 
		} 
		
		let category;
		if(this.state.category.length > 1) {
			category = values.tags.join(", ") 
		} else {
			category = values.tags
		}

        let newItem = {
            id: this.props.match.params.id, 
			userId: this.props.match.params.id, 
			title: values.title, 
			tags:  values.tags, 
			description: this.state.description, 
			category: category, 
			progammingLanguage: this.state.progLang 
        }

		// console.log(newItem)
		/* console.log("ID :" + newItem.id + " USER_ID: " + newItem.userId + " TITLE: " + 
						newItem.title + " TAGS: " + newItem.tags + " DESC: " + newItem.description + 
						" PROGLANG: " + newItem.progammingLanguage + " CATEGORIES: " + newItem.category)
		*/

        if (this.state.id == -1) {
            BugDataService.createItem(newItem);
			console.log("Create bug report successful.")
			this.state.fix.map((data, index) => {
				data.id = index
				console.log(data.id)
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

	onInputChange(target, event) {
		if(target === "desc") {
			try { this.setState({data: event, description: event}); } catch (ex) { }
			return;
		} else if(target === "category") {
			let arr = Array.from(event.target.selectedOptions, option => option.value) 
			this.setState({value: arr, category: arr}); 
			return;
		} else {
			this.setState({value: event.target.value, [target]: event.target.value}); 
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
	
	onFixDelete(index) {
		var arr = this.state.fix.filter(f => f.id != index + 1); // Fix array counts from 1, not 0
		this.modifyFix(arr); 
		console.log("Fix no." + index + " deleted");
	}
	
	modifyFix(arr) {
		this.setState({fix: arr.map((x) => ({
						  id: x.id, 
						  noOfTimesWorked: x.noOfTimesWorked, 
						  title: x.title, 
						  solution: x.solution, 
						  bug: this.state.item
				        }))		
					});	
	}
	
	backBtnOnClick() {
	    this.props.history.push(``)
	}

    render() {
		let { id, title, progLang, category, description, fix } = this.state
		const { tags, tagsChange } = this.state.tags; 
		const progLangOptions = PROGLANG; 

	    return (
	        <div>
				<BreadcrumbUI id={this.state.id} condition="EDIT" name={this.state.item.title} history={this.props.history} />
                <Formik initialValues={{ id, title, progLang, tags, category, description, fix }} 
					onSubmit={this.onSubmit}
					validateOnChange={false} validateOnBlur={false}
				    validate={this.validate} enableReinitialize={true} > 
						{(props) => (
                            <Form className="row ml-5">
								{/* "BUG" PANEL */}
								<div className="col-7 bg-light pt-4 col shadow">
									<h3><Icon name="bug" />Bug Report</h3>
									<br/>
									
									{/* 1st ROW: NAME & PROGRAMMING LANGUAGE */}
									<Label color='red' ribbon>Name of the Bug </Label> 
									&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
									&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;
									<Label className="ml-5" color='red' ribbon>Programming Language </Label> <br/>
									<input className="col-7" type="text" name="title" defaultValue={this.state.item.title}
										placeholder="e.g. Cx001: OutOfBoundsException" 
										onChange={(event) => {this.onInputChange("title", event);}} 
										 />
									&emsp;&nbsp;
								    <select id="progLang" name="progLang"
					                    onChange={(event) => {this.onInputChange("progLang", event);}}>
										{ progLangOptions.map((lang, index) => {
											if(lang === progLang) { return <option key={index} selected>{lang}</option>; }
						                  	return <option>{lang}</option>;
						                })}
									 </select>
									<br/><br/>
									
									{/* 2nd ROW: CATEGORY */}
									<Label class="ml-5" as='a' color='red' ribbon>Category </Label> 
											* Press Ctrl to select multiple <br/>
								    <SelectCategoriesInput selected={category} onInputChange={this.onInputChange} />
									<br/><br/>
									
									{/* 3rd ROW: DESCRIPTION */}
									<Label as='a' color='red' ribbon>Description</Label> <br/>
									<CKEditor
										id="ckeditor" 
										name="desc"
										data={description}
					                    editor={ClassicEditor}
					                    placeholder="<p>e.g. Cause, Steps to reproduce...</p>"
					                    onChange={(event, editor) => {
									              	const data = editor.getData();
									                this.onInputChange("desc", data);
									}}/>
									<br/>
									
									{/* 4th ROW: TAGS */}
									<Label as='a' color='red' ribbon>Tags </Label>
                                    <TagsInput className="react-tagsinput"
										name="tags"
										list="tagSuggest"
								        value={this.state.tags}
								        onChange={this.tagChange}
								        onChangeInput={this.tagChangeInput}
								        validationRegex={this.REGEXP}
										inputValue={tags}
								        onlyUnique addOnBlur addOnPaste
								        pasteSplit={this.onPaste}/>
									<br/>
									<br/><br/><br/><br/>
							</div>
							
							{/* "SOLUTIONS" PANEL */}
							<div className="col-5 bg-light shadow">	
								<br/>
								<h3 id="fixListTitle">
									<Icon name="lightbulb outline" />Solutions</h3>
								<List id="dragList">
									
						            {this.state.fix.map((data, index) => {
								         return <div className="fixDiv shadow" key={index}>
												<CreateSolutionModal purpose="edit" modifyFix={this.modifyFix}
													fixList={this.state.fix} newFixItem={data} bug={this.state.item} />
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
									<CreateSolutionModal purpose="add" modifyFix={this.modifyFix}
										fixList={this.state.fix} newFixItem={[]} bug={this.state.item} />
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