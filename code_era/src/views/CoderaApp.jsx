import React, { Component } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faObjectGroup } from '@fortawesome/free-solid-svg-icons';

import { Redirect } from 'react-router-dom';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'; 

import ListItemView from './ListItemView';
import ReadItemView from './ReadItemView';
import CreateItemView from './CreateItemView';

/* TOP BAR & LOGO, ALWAYS CALLED IN App.js */
class CoderaApp extends Component {
	
	constructor(props) {
        super(props)
	}
	
	/* REDIRECT TO ListItemView WHEN "CODERA" LOGO IS CLICKED */
	homeBtnOnClick() {
	    return <Redirect to={{ pathname: "/" }} />
	}
	
    render() {
        return (
			<div>
				{/* TOP BAR & LOGO */}
				<div className="jumbotron" style={{ backgroundColor: "#7f0019" }}>
					<p id="pageDesc" className="text-uppercase text-monospace font-weight-light mb-auto">
						My programming error and solution storage
					</p>
					{/* CODERA HEADER */}
	                <h1 className="font-weight-light mt-auto mb-auto" onClick={() => {
						return <Redirect to={{ pathname: "/login" }} />}} style={{ textAlign: "left", 
						color: "white", fontFamily: "Nunito thin", fontWeight: "thin",fontSize: "37px" }}>
						<FontAwesomeIcon icon={ faObjectGroup } color="#f5f5dc" /> 
						CODERA
					</h1>
					{/* HORIZONTAL LINE */}
				    <hr className="mt-auto" color="white" height="14px" />
				</div> 
				
				{/* ALL PAGE TO URL NAVIGATIONS ARE MAPPED HERE */}
				<Router>
                    <Switch>
						<Route path="/user" exact component={CoderaApp} />
                        <Route path="/" exact component={ListItemView} />
                        <Route path="/bugs" exact component={ListItemView} />
						<Route path="/bugs/:id/view" component={ReadItemView} />
                        <Route path="/bugs/:id/" component={CreateItemView} />
                    </Switch>
           	    </Router>
			</div>
        )
    }
}

export default CoderaApp