package com.codeEra.code_era.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.bytebuddy.asm.Advice.This;

import javax.persistence.Column;

import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

/**
 * A Bug object generator. <p>Using Java persistence annotations to auto-generate table columns, 
 * and map relationship with other objects. 
 */
@Entity
@Table(name="Bug")
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class Bug {
	
	/** Unique identifier of the Bug object. */
	@Id
	@GeneratedValue // Auto-increment for id
	@Column(name = "BUG_ID", columnDefinition="NUMBER(6)")
	private long id;
	
	/** The author/user who write this Bug report. */
	@Column(name = "USER_ID", columnDefinition="NUMBER(6)", nullable = false)
	private long userId;
	
	/** The title of the Bug. */
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	/** The programming language the code is using. */
	@Column(name = "PROG_LANG", nullable = false)
	private String progLang; 
	
	/**  The description of how this bug is produced. */
	@Lob
	@Column(name = "DESCRIPTION", nullable = false)
	private String desc;
	
	/** The categories related to this bug. */
	@Column(name = "CATEGORIES", nullable = false)
	private String categories;
	
	/** The tags applied to this bug. */
	@Column(name = "TAGS")
	private String tags; 
	
	/** The solutions belonged to this bug. */
	@Column(name = "FIXES")
	@OneToMany(targetEntity=Fix.class, mappedBy="bug")
	private Set<Fix> fixes; 
	
	/** Empty Constructor */
	public Bug() {
	}
	
	/** Constructor for inserting new record in the database.  
	 * @param userId ID of the Bug report's author 
	 * @param title title of the Bug report  
	 * @param description content of the Bug report  
	 * @param programmingLanguage the programming language the bug code uses 
	 * @param categories the categories this Bug report belongs to 
	 * @param tags the tags applied to this Bug report 
	 */
	public Bug(String categories, String programmingLanguage, 
			String tags, String title, long userId, String description) {
		this.setUserId(userId);
		this.setTitle(title); 
		this.setProgammingLanguage(programmingLanguage); 
		this.setCategory(categories); 
		this.setTags(categories); 
		this.setDescription(description); 
	}
	
	/** 
	 * Constructor for updating new record in the database.
	 * @param userId ID of the Bug report's author 
	 * @param id ID of the Bug object 
	 * @param title title of the Bug report  
	 * @param description content of the Bug report  
	 * @param programmingLanguage the programming language the bug code uses 
	 * @param categories the categories this Bug report belongs to 
	 * @param tags the tags applied to this Bug report 
	 */
	public Bug(String categories, String programmingLanguage, 
			String tags, String title, long userId, long id, 
			String description) {
		this.setUserId(userId);
		this.setId(id);
		this.setTitle(title); 
		this.setProgammingLanguage(programmingLanguage); 
		this.setCategory(categories); 
		this.setTags(tags); 
		this.setDescription(description); 
	}
	
	/**
	 * Get the author/user of this bug. 
	 * @return the _userId
	 */
	public long getUserId() {
		return userId;
	}
	

	/**
	 * Set author for this bug. 
	 * @param _userId the _userId to set
	 */
	public void setUserId(long _userId) {
		this.userId = _userId;
	}

	/**
	 * Get the author of this bug. 
	 * @return the _id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set unique identifier of this bug. 
	 * @param id the ID to be given to the bug. 
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get the title of the bug. 
	 * @return the title of the bug. 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Stores user's input in the "Name of the Bug" text field.   
	 * @param title the title to set. 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the programming language the code is using.  
	 * @return the programming language the code is using.  
	 */
	public String getProgammingLanguage() {
		return progLang;
	}	

	/**
	 * Stores user's selection in the dropdown UI for programming language.  
	 * @param progLang the programming language to set. 
	 */
	public void setProgammingLanguage(String progLang) {
		this.progLang = progLang;
	}	

	/**
	 * Get categories related to this bug.
	 * @return the categories this bug belongs to. 
	 */
	public String getCategory() {
		return categories;
	}

	/**
	 * Stores user's multiple input in the "Categories" multiple select UI, and 
	 * will be stored as a string with <i>", "</i> to separate between each category.  
	 * @param categories the categories to set. 
	 */
	public void setCategory(String categories) {
		this.categories = categories;
	}
		
	/**
	 * Get tags related to this bug. 
	 * @return the tags this bug applies to. 
	 */
	public String getTags() {
		return tags;
	}
	
	/**
	 * Stores user's input in the "Tags" text field.   
	 * @param tags the tags to be applied to. 
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}


	/**
	 * Get description of how this bug is produced.
	 * @return the description for the bug. 
	 */
	public String getDescription() {
		return desc;
	}
	
	/**
	 * Stores user's input in the "Description" text field.  
	 * @param desc the description to set
	 */
	public void setDescription(String desc) {
		this.desc = desc;
	}

	/**
	 * Get a list of solutions related to this bug.
	 * @return all solutions mapped to this bug.
	 */
	public Set<Fix> getFix() {
		return fixes;
	}

	/**
	 * Stores user's input in the "Solutions" panel.   
	 * @param fixes the _fix to set
	 */
	public void setFix(Set<Fix> fixes) {
		this.fixes = fixes;
	}
	
	/**
	 * An equals method   
	 * @param obj the object to be checked 
	 * @return whether the targeted object is equal to this Bug 
	 * @override the equals() of Object class 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Bug) {
			if(((Bug) obj).getId() == this.getId() && 
					((Bug) obj).getUserId() == this.getUserId() && 
					((Bug) obj).getTitle().equals(this.getTitle()) &&
					((Bug) obj).getProgammingLanguage().equals(this.getProgammingLanguage()) &&
					((Bug) obj).getCategory().equals(this.getCategory()) &&
					((Bug) obj).getTags().equals(this.getTags()) &&
					((Bug) obj).getFix().hashCode() == this.getFix().hashCode()) {
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
		return "BUG DETAILS: \n" + "ID: " + this.getId() + "\n TITLE: " + this.getTitle() + "\n LANGUAGE USED: " + 
				this.getProgammingLanguage() + "\n CATEGORY: " + this.getCategory() + "\n DESCRIPTION: " + 
				this.getDescription() + "\n TAGS: " + this.getTags() + "\n FIX: " + this.getFix();
	} 
}

