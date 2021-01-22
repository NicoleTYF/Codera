/**
 * 
 */
package com.codeEra.code_era.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * A Fix object generator. <p>Using Java persistence annotations to auto-generate table columns, 
 * and map relationship with other objects. 
 */
@Entity
@Table(name="Fix")
public class Fix {
	
	/** Unique identifier of the Fix object. */
	@Id
	@GeneratedValue
	@Column(name="FIX_ID", nullable=false)
	private long id;

	/** The title of the solution. */
	@Column
	private String title; 

	/**  The content of the solution. */
	@Lob
	@Column
	private String solution; 
	
	/**  The number of time this solution worked. */
	@Column
	private int no_of_times_worked;

	/**  The bug this solution belongs to. */
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, optional = false)
    @JoinColumn(name="id", nullable=false) 
	@JsonIgnore
	private Bug bug; 
	public Bug getBug() {
		return bug; 
	}
	public void setBug(Bug b) {
		this.bug = b;
	}
	
	/**  Empty constructor. */
	public Fix() {
		super();
	}
	
	/**  
	 * Constructor used in inserting a record in the database. 
	 * @param title title of the solution 
	 * @param solution content of the solution 
	 * @param noOfTimesWorked the number of time this solution worked 
	 * @param bug the Bug object this Fix belongs to 
	 */
	public Fix(String title, String solution, int noOfTimesWorked, Bug bug) {
		super();
		this.setTitle(title);
		this.setSolution(solution); 
		this.setNoOfTimesWorked(noOfTimesWorked);
		this.setBug(bug);
	} 
	
	/**  
	 * Constructor used in updating a record in the database.
	 * @param id ID of the Fix object 
	 * @param title title of the solution 
	 * @param solution content of the solution 
	 * @param noOfTimesWorked the number of time this solution worked 
	 * @param bug the Bug object this Fix belongs to 
	 */
	public Fix(long id, String title, String solution, int noOfTimesWorked, Bug bug) {
		super();
		this.setId(id);
		this.setTitle(title);
		this.setSolution(solution); 
		this.setNoOfTimesWorked(noOfTimesWorked);
		this.setBug(bug);
	} 
	
	/**
	 * Get the ID of the solution. 
	 * @return the unique identifier of the solution 
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Store the ID of the solution. 
	 * @param id the ID to be set 
	 */
	public void setId(long id) { 
		this.id = id;
	}
		

	/**
	 * Get the title of this solution. 
	 * @return the title of this solution 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Store user's input in the "Title" text field in the "Add Solution" pop up box. 
	 * @param title the title to set 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the content of the solution. 
	 * @return the content of the solution 
	 */
	public String getSolution() {
		return solution;
	}

	/**
	 * Store user's input in the "Description" text field in the "Add Solution" pop up box. 
	 * @param _solution the _solution to set
	 */
	public void setSolution(String _solution) {
		this.solution = _solution;
	}

	/**
	 * Get the number of time this solution worked.  
	 * @return the number of times this solution worked 
	 */
	public int getNoOfTimesWorked() {
		return no_of_times_worked;
	}

	/**
	 * Stores user's input of toggling the "this works!" button.  
	 * @param noOfTimesWorked the number of this time worked to set 
	 */
	public void setNoOfTimesWorked(int noOfTimesWorked) {
		this.no_of_times_worked = noOfTimesWorked;
	}
	
	/**
	 * An equals method   
	 * @param obj the object to be checked 
	 * @return whether the targeted object is equal to this Fix 
	 * @Override the equals() of Object class 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Fix) {
			if(((Fix) obj).getId() == this.getId() && 
					((Fix) obj).getTitle() == this.getTitle() && 
					((Fix) obj).getSolution().equals(this.getSolution()) &&
					((Fix) obj).getNoOfTimesWorked() == this.getNoOfTimesWorked() &&
					((Fix) obj).getBug().equals(this.getBug())) {
				return true;
			}
			return false;
		}
		return false;
	} 
	
	/**
	 * Turn the object into string.  
	 * @return whether the targeted object is equal to this Bug 
	 * @override the toString() of Object class 
	 */
	@Override
	public String toString() {
		return "FIX DETAILS: \n" + "ID: " + this.getId() + "\n TITLE: " + this.getTitle() + "\n SOLUTION: " + 
				this.getSolution() + "\n # OF TIMES WORKED: " + this.getNoOfTimesWorked() + 
				"\n BUG: " + this.getBug(); 
	} 
}
