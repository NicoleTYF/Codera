package com.codeEra.code_era.controller;

// Communicating with the server
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

//Spring boot data handling 
import java.util.List;
import org.springframework.data.domain.Pageable;
//Spring boot annotations
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
//File references
import com.codeEra.code_era.model.Bug;
import com.codeEra.code_era.service.BugService;
import com.codeEra.code_era.exception.ResourceNotFoundException;

/**
 * APIs to create, retrieve, update, and delete {@link com.codeEra.code_era.model.Bug} objects. 
 */
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@Controller
@RestController
public class BugController {
	
	  /** The data store of the Bug table. */
	  @Autowired
	  private BugService bugService;
	  
	  /** 
	   * GET API for retrieving all Bugs from the data store. 
	   * @param username the username of this author 
	   * @param pageable the list to store the retrieved Bugs 
	   * @return all the Bugs in the data store 
	   */
	  @GetMapping("/{username}/bugs/")
	  public List<Bug> getAllItems (@PathVariable String username, Pageable pageable) {
		  return this.bugService.findAllByOrderByIdDesc(pageable);
	  }
	  
	  /** 
	   * GET API for retrieving a Bug by {@link com.codeEra.code_era.model.Bug#id}.
	   * @param username the username of the author 
	   * @param id the ID of the Bug to be retrieved 
	   * @return the Bug found in the data store with the ID 
	   */
	  @GetMapping("/{username}/bugs/{id}")
      public Bug getItem (@PathVariable String username, @PathVariable long id) {
		  return this.bugService.getOne(id);
      }
	  
	  /** 
	   * POST API for creating a Bug object in the data store.
	   * @param username the username of the author 
	   * @param item the input item to bee created 
	   * @return Server response indicating the item is created 
	   */
	  @PostMapping("/{username}/bugs/")
	  public ResponseEntity<Bug> createItem (@PathVariable String username, @RequestBody Bug item) {
		   try {
		      Bug createdItem = bugService.create(item);
		      return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	  }
	  
	  /** 
	   * PUT API for updating a Bug object in the data store. 
	   * @param username the username of the author 
	   * @param id the ID of the Bug to be updated 
	   * @param postRequest the input item from the frontend 
	   * @return Server response indicating the item is updated 
	   * @exception ResourceNotFoundException return the message that the item cannot be found  
	   */
	  @PutMapping("/{username}/bugs/{id}")
	  public ResponseEntity<Bug> updateItem (@PathVariable String username, 
			  @PathVariable long id, @Valid @RequestBody Bug postRequest) {
		  	// Set variables to create the item
	        bugService.updateAndSave(id, postRequest);
	        return new ResponseEntity<Bug>(postRequest, HttpStatus.OK);
	  }
	  
	  /** 
	   * DELETE API for deleting a Bug object in the data store.
	   * @param username the username of the author 
	   * @param id the ID of the Bug to be deleted 
	   * @return Server response indicating the item is deleted 
	   * @exception ResourceNotFoundException return the message that the item cannot be found  
	   */
	  @DeleteMapping("/{username}/bugs/{id}")
	  public ResponseEntity<?> deleteItem (@PathVariable String username, @PathVariable long id) {
		  return bugService.deleteItem(id); 
	  }
	  
	  /** 
	   * GET API for retrieving all Bugs from the data store by categories. 
	   * @param username the username of this author 
	   * @param pageable the list to store the retrieved Bugs 
	   * @return all the Bugs in the data store 
	   */
		/*
		 * @GetMapping("/{username}/bugs/category='{category}'") public List<Bug>
		 * getListByCategory (@PathVariable String username, Pageable pageable,
		 * 
		 * @PathVariable String category) { return
		 * this.bugService.findByCategories(category, pageable).getContent(); }
		 */
}
