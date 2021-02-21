package com.codeEra.code_era.controller;

// Communicating with the server
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
// Spring boot data handling 
import java.util.List;
import org.springframework.data.domain.Pageable;
// Spring boot annotations
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
// URI builder
import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
// File references
import com.codeEra.code_era.model.Fix;
import com.codeEra.code_era.service.BugService;
import com.codeEra.code_era.service.FixService;
import com.codeEra.code_era.exception.ResourceNotFoundException;

/** 
 * APIs to create, retrieve, update, and delete {@link com.codeEra.code_era.model.Fix} table records
 */
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@Controller
@RestController
public class FixController {
	  
	  /** The data store of the Bug table. */
	  @Autowired
	  private FixService fixService;

	  /** 
	   * GET API for retrieving all Fixes from the data store. 
	   * @param username the username of this author 
	   * @param parentId the ID of the Bug object to retrieve its Fixes for 
	   * @param pageable the list to store the retrieved Fixes 
	   * @return all the Fixes in the data store 
	   */
	  @GetMapping("/{username}/bugs/{parentId}/fixes")
	  public List<Fix> getAllItem(@PathVariable String username, 
			  @PathVariable (value = "parentId") long parentId, Pageable pageable) {
		  return fixService.findByBugId(parentId, pageable);
	  } 
	  
	  /** 
	   * POST API for creating a Fix object in the data store.
	   * @param username the username of the author 
	   * @param parentId the ID of the Bug object to retrieve its Fixes for 
	   * @param createdItem the input item to bee created 
	   * @return Server response indicating the item is created 
	   */
	  @PostMapping("/{username}/bugs/{parentId}/fixes/{id}")
	  public ResponseEntity<?> createItem(@PathVariable String username, 
			  @PathVariable (value = "parentId") long parentId,
              @Valid @RequestBody Fix createdItem) {
		  
		  // Set variables to update the item
	      fixService.create(parentId, createdItem); 

	      // If no ResourceNotFoundException returned, return OK HttpStatus
          return new ResponseEntity<Fix>(createdItem, HttpStatus.OK);
	  }
	  
	  /** 
	   * PUT API for updating a Fix object in the data store. 
	   * @param parentId the ID of the Bug object to retrieve its Fixes for 
	   * @param id the ID of the Fix to be updated 
	   * @param postRequest the input item from the frontend 
	   * @return Server response indicating the item is updated 
	   * @exception ResourceNotFoundException return the message that the item cannot be found  
	   */
	  @PutMapping("/{username}/bugs/{parentId}/fixes/{id}")
	  public ResponseEntity<?> updateItem(@PathVariable (value = "parentId") long parentId,
	                             @PathVariable (value = "id") long id,
	                             @Valid @RequestBody Fix postRequest) {
		fixService.updateItem(parentId, id, postRequest);
		
        // If no ResourceNotFoundException returned, return OK HttpStatus
        return new ResponseEntity<Fix>(postRequest, HttpStatus.OK);
	  }
	  
	  /** 
	   * DELETE API for deleting a Bug object in the data store.
	   * @param parentId the ID of the Bug object to retrieve its Fixes for 
	   * @param id the ID of the Fix to be deleted 
	   * @return Server response indicating the item is deleted 
	   * @exception ResourceNotFoundException return the message that the item cannot be found  
	   */
	  @DeleteMapping("/{username}/bugs/{parentId}/fixes/{id}")
	  public ResponseEntity<?> deleteItem(@PathVariable (value = "parentId") long parentId,
	                            @PathVariable (value = "id") long id) {
	     fixService.deleteItem(parentId, id);
	     
	     // If no ResourceNotFoundException returned, return OK HttpStatus
	     return ResponseEntity.ok().build();
	  }
	  
}
