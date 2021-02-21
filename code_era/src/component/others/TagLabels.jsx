import React, { Component } from 'react'

import { Label } from "semantic-ui-react" 


class TagsLabels extends React.Component{

	constructor(props) {
		super(props) 

	}
	
	TagsLabels()	{	
			
		// If array is not empty, split the tagList (string)
		if(this.props.tags) {
			if(this.props.tags.includes(", ")) {
				return <div>
				{this.props.tags.split(", ").map((tag, index) => {
                  return <Label as='a' tag color="teal" class="p-4 ml-2">
					        {tag} </Label>;
                })}</div>;
			}
			
			return <div>
				<Label as='a' tag color="teal" class="p-4 ml-2">
					        {this.props.tags} </Label>
                </div>;
			
		} 
	}
	
	render() {
		return (<div>{this.TagsLabels()}</div>)
	}
}

export default TagsLabels