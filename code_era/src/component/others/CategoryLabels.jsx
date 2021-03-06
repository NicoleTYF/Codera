import React from 'react'

class CategoryLabels extends React.Component{
	
	CategoryLabels() {	
			
		// If array is not empty, split the tagList (string)
		if(this.props.categories) {
			if(this.props.categories.includes(", ")) {
				return <span>
				{this.props.categories.split(", ").map((c) => {
						return c + " • ";
				})}</span>;
			}
			
			return <span> {this.props.categories} </span>;
		} 
	}
	
	render() {
		return (<span style={{ "color": "#7f0019", "fontWeight": "bold"}} > {this.CategoryLabels()} </span>)
	}
}

export default CategoryLabels 