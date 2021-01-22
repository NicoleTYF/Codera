import React, { Component } from 'react'

import { Label } from "semantic-ui-react" 


class TagsLabel extends React.Component{
	
	constructor(props) {
		super(props) 
	}
	
	TagsLabel()	{		
		return <div>
				{this.props.tags.split(", ").map((tag, index) => {
                  return <Label as='a' tag color="teal" class="p-4 ml-2">
					        {tag} </Label>;
                })}</div>;
	}
	
	render() {
		return (<div>{this.TagsLabel()}</div>)
	}
}

export default TagsLabel