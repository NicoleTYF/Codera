import React from 'react'

import { Breadcrumb } from "semantic-ui-react" 


class BreadcrumbUI extends React.Component {
	
	backBtnOnClick() {
	    this.props.history.push(``)
	}
	
	checkInfo()	{		
		if(this.props.id == -1) {
		    return <Breadcrumb class="breadcrumb ui attached top">
				    	<Breadcrumb.Section title="Go to Home page" link>
							<button onClick={() => this.backBtnOnClick()}>Home</button>
					    </Breadcrumb.Section>
				   	    <Breadcrumb.Divider icon='right angle' />
				   		<Breadcrumb.Section active>
				  		    add Post
				   		</Breadcrumb.Section>
				  </Breadcrumb>  
		} else if(this.props.condition === "EDIT") {
			return <Breadcrumb className="breadcrumb ui attached top segment">
					    <Breadcrumb.Section title="Go to Home page" link>
							<button  onClick={() => this.backBtnOnClick()}>Home</button></Breadcrumb.Section>
					    <Breadcrumb.Divider icon='right angle' />
					    <Breadcrumb.Section title="View post" link>
							<button href="" onClick={() => this.props.history.goBack()} 
							className="truncatecategory">
								Post #{this.props.id}: {this.props.name}</button>
						</Breadcrumb.Section>
					  	<Breadcrumb.Divider icon='right angle' />
						<Breadcrumb.Section active> edit </Breadcrumb.Section>
					</Breadcrumb> 
		} else {
			return (<div><Breadcrumb className="ui attached top segment">
				    <Breadcrumb.Section title="Go to Home page" link><a onClick={
						() => this.backBtnOnClick()}>Home</a></Breadcrumb.Section>
				    <Breadcrumb.Divider icon='right angle' />
				    <Breadcrumb.Section active>
				       <span className="truncatecategory">view Post #{this.props.id}: {this.props.name}</span>
				    </Breadcrumb.Section>
				  </Breadcrumb> <div>{this.props.children} </div></div>) 
				   
		}
	}
	
	render() {
		
		return (<div>{this.checkInfo()}</div>)
	}
}

export default BreadcrumbUI