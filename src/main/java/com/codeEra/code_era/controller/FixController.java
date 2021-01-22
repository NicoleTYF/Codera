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
import com.codeEra.code_era.repository.BugRepository;
import com.codeEra.code_era.repository.FixRepository;
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
	  private FixRepository fixRepository;
	  
	  /** The data store of the Fix table. */
	  @Autowired
	  private BugRepository bugRepository;

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
		  return fixRepository.findByBugId(parentId, pageable).getContent();
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
		  
		  URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
		    		buildAndExpand(createdItem.getId()).toUri();
		  
		  // Set variables to update the item
	      return bugRepository.findById(parentId).map(itemParent -> { 
			    createdItem.setBug(itemParent);
			    fixRepository.save(
			    		new Fix(createdItem.getId(), 
					    createdItem.getTitle(), 
					    createdItem.getSolution(), 
					    createdItem.getNoOfTimesWorked(), 
			    		createdItem.getBug()));
			    ResponseEntity.created(uri).build();
	            return new ResponseEntity<Fix>(createdItem, HttpStatus.OK);
	      }).orElseThrow(() -> new ResourceNotFoundException("Parent ID " + parentId + " not found"));
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
		if(!bugRepository.existsById(parentId)) {
		    throw new ResourceNotFoundException("Bug ID " + parentId + " not found");
		}
		
		// Set variables to update the item
        return fixRepository.findById(id).map(updateItem -> {
        	updateItem.setId(postRequest.getId()); 
		    updateItem.setTitle(postRequest.getTitle()); 
		    updateItem.setSolution(postRequest.getSolution()); 
		    updateItem.setNoOfTimesWorked(postRequest.getNoOfTimesWorked()); 
		    updateItem.setBug(bugRepository.findById(parentId).get());
		    fixRepository.save(updateItem);
            return new ResponseEntity<Fix>(updateItem, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("BugId " + id + " not found"));
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
	        return fixRepository.findByIdAndBugId(id, parentId).map(deleteItem -> {
	            fixRepository.delete(deleteItem);
	            return ResponseEntity.ok().build();
	        }).orElseThrow(() -> new ResourceNotFoundException("Fix not found with ID " + id + 
	        		" and parent ID " + parentId));
	  }
	  
}
