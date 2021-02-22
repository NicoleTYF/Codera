import React, { Component } from 'react'

import BugDataService from '../service/BugDataService.js'; 

import { PROGLANG } from '../data_progLang.js';
import { groupedOptions } from '../data_categories.js';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEdit } from '@fortawesome/free-solid-svg-icons'
import { faTrashAlt } from '@fortawesome/free-solid-svg-icons'
import { Label, Icon, Popup, Divider, Header } from 'semantic-ui-react' 

import TagLabels from './../component/others/TagLabels'; 
import CategoryLabels from './../component/others/CategoryLabels'; 

import CheckBoxSelect from 'react-multiselect-checkboxes';

class ListItemView extends Component {

	constructor(props) {
        super(props)
        this.state = {
            data: [],
            message: null, 
			categorySearch: [] // NOT IMPLEMENTED YET
        }
		this.refreshList = this.refreshList.bind(this)
		this.addBtnOnClick = this.addBtnOnClick.bind(this)
		this.actionBtnOnClick = this.actionBtnOnClick.bind(this)
    }

	/* FUNCTIONS CALLED ON START */
    componentDidMount() {
		this.refreshList(); 
  	}

	/* RETRIEVE ALL RECORDS ON START / ON DELETE  */
	refreshList() {
    	BugDataService.retrieveList() 
        .then(response => {
                console.log(response);
				this.setState({ data: response.data })
            }
        )
     }
	
	/* RETRIEVE ALL RECORDS ON START / ON DELETE */
	addBtnOnClick() {
	    this.props.history.push(`/bugs/-1`)
	}
	
	/* ADD, UPDATE OR DELETE RECORD FROM THE LIST */
	actionBtnOnClick(purpose, id) {
		if(purpose === "delete") {
			console.log('delete ' + id)
			BugDataService.deleteItem(id)
        	.then(response => {
                this.setState({ message: `Delete item ${id} Successful` })
				this.props.history.push(``)
            })
		}
		
		if(purpose === "view") {
			console.log('view ' + id)
		    this.props.history.push(`/bugs/${id}/view`)
		} else if(purpose === "update") {
			console.log('update ' + id)
	   	    this.props.history.push(`/bugs/${id}`) 
		} 
	}
	
	/* SEARCH THE LIST WITH THE INPUTS FROM SERACH BAR */
	searchItems(searchTerm) {
		const countItems = this.state.data.filter(item => 
			item.name.toLowerCase().includes(searchTerm.toLowerCase()));
   		return countItems.length;
    }

    render() {
		const progLangOptions = PROGLANG; 
		const categoryOptions = groupedOptions; 
		
        return (
			<div className="row">
				{/* LEFT PANEL */}
	            <div id="List" className="col-8">
					<br/>
					{/* HIDDEN MESSAGE BOX, CALLED WHEN AN ITEM IS DELETED */}
					{this.state.message && <div className="alert alert-success">
						{this.state.message}</div>}
						
					<h2>ALL RECORDS</h2>
	 				<div className="ui action input ml-auto t-0 p-3">
				
						{/* SEARCH BAR */}
						<input type="text" placeholder="Search..." id="searchBar" />
						<button className="ui icon button" id="searchBtn"> 
							<Icon aria-hidden="true" name="search" />
						</button>
						{/* "+ ADD NEW"  BUTTON */}
						<button className="col-2 ui btn btn-sm button attached right" id="addBtn" 
								onClick={() => this.addBtnOnClick()}>
							&#x002B;&nbsp; ADD NEW 
						</button>
					</div>
					{/* RECORDS LIST */}
	                <div>
	                    <table className="table ml-3">
	                        <tbody>
							   {/* MAP EACH RECORD DATA INTO A BOX */}
	                   	       {this.state.data.map(data =>
	                                <tr key={data.id}> 
										<div className="row d-flex pl-2 ml-3 mt-3" id="categoryText"> 
											{/* PROGRAMMING LANGUAGE OF THE RECORD */} 
											<Label horizontal id="progLangtext">{data.progammingLanguage}</Label>
											<p> | &nbsp; 
											
											{/* LIST ALL CATEGORIES OF THE RECORD */}
											<CategoryLabels categories={data.category} />
											</p><br/>  
											
											{/* TOP LEFT LIGHT BULB ICON */}
											<Popup content={data.fix.length + " Solution(s)"} trigger={
												<div className="d-flex actionTools ml-auto mr-3 solu">
													<Icon name="lightbulb outline" /> 
													{data.fix.length} &emsp;&nbsp;
												</div>}/>
										</div>
										<div className="pl-3 ml-3 pr-3">
											{/* TITLE */}
											<u><h3 className="truncate" onClick={() => {this.actionBtnOnClick(
												"view", data.id)} }>{data.title}
											</h3></u>
											<br/>
											
											{/* DESCRIPTION */}
	                               			<div className="desc truncate text-muted" 
												 onClick={() => this.actionBtnOnClick("view", data.id)}
												 dangerouslySetInnerHTML={{__html: data.description}} />
											<br/>
											
											{/* TAGS */}
											<TagLabels className="float-right mr-4" id="tagList" tags={data.tags} />
											
											<div className="d-flex actionTools ml-auto mr-3 b-5"> 
												{/* EDIT BUTTON */}
												<button className="align-self-start btn btn-outline-light"
													onClick={() => this.actionBtnOnClick("update", data.id)}>
													<FontAwesomeIcon icon={ faEdit } color="teal" size="lg" />
												</button>
												&emsp;
												{/* DELETE BUTTON */}
												<button className="align-self-start btn btn-outline-light" 
													onClick={() => this.actionBtnOnClick("delete", data.id)}>
													<FontAwesomeIcon icon={ faTrashAlt } color="coral" size="lg" />
												</button>
											</div> <p/><p/> </div>
	                                </tr>
                          		  )}
	                        </tbody>
	                    </table>
	                </div>
	            </div>
				{/* RIGHT PANEL */}
				<div className="col-3 ml-4"> 
					<br/>
					<Divider horizontal>
				      <Header as='h4'>
				        <Icon name='filter' />
				        Filters
				      </Header>
				    </Divider>
					{/* CATEGORY FILTER */}
					<h5>By Category: </h5>
					<CheckBoxSelect id="categoryOptions" options={categoryOptions} />
					<br/>
					{/* PROGRAMMING LANGUAGE FILTER */}
					<h5>By Programming Language: </h5>
					<CheckBoxSelect options={progLangOptions} />
				</div>
			</div>
        )
    }
}

export default ListItemView