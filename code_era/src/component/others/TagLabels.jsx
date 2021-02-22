import React from 'react'

import { Label } from "semantic-ui-react" 


class TagsLabels extends React.Component{
	
	TagsLabels() {	
			
		// If array is not empty, split the tagList (string)
		if(this.props.tags) {
			if(this.props.tags.includes(", ")) {
				return <div>
				{this.props.tags.split(", ").map((tag, index) => {
                  return <Label tag key={index} color="teal" className="tagLabels">
					        {tag} </Label>;
                })}</div>;
			}
			
			return <div>
				<Label as='a' tag color="teal" className="tagLabels">
					        {this.props.tags} </Label>
                </div>;
			
		} 
	}
	
	render() {
		return (<div>{this.TagsLabels()}</div>)
	}
}

export default TagsLabels