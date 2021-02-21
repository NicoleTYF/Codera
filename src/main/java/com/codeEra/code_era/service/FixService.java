package com.codeEra.code_era.service;

import java.net.URI;
// Utilities
import java.util.List;

// Spring Data
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// Other Classes
import com.codeEra.code_era.model.Bug;
import com.codeEra.code_era.model.Fix;
import com.codeEra.code_era.repository.FixRepository;
import com.codeEra.code_era.exception.ResourceNotFoundException;


/**
 * Handles additional logic behind {@link com.codeEra.code_era.model.FixController} REST API methods.
 */
@Service
@Transactional
public class FixService {

	/** The data store of the Bug table. */
    @Autowired
    private FixRepository fixRepository;
  
    /** The data store of the Fix table. */
    @Autowired
    private BugService bugService;

    /** 
	 * GET API for retrieving all Fixes from the data store. 
	 * @param parentId the ID of the Bug object to retrieve its Fixes for 
	 * @param pageable the list to store the retrieved Fixes 
	 * @return all the Fixes in the data store 
	 */
    public List<Fix> findByBugId(long parentId, Pageable pageable) {
    	return fixRepository.findByBugId(parentId, pageable).getContent();
	} 
	
    /** 
     * POST API for creating a Fix object in the data store.
     * @param username the username of the author 
     * @param parentId the ID of the Bug object to retrieve its Fixes for 
     * @param createdItem the input item to bee created 
     * @return Server response indicating the item is created 
     */
	public Bug create(long parentId, Fix item) {
		  
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
	    		buildAndExpand(item.getId()).toUri();
	  
	    // Set variables to update the item
        return (bugService.findById(parentId)).map(itemParent -> { 
		    item.setBug(itemParent);
		    fixRepository.save(
		    		new Fix(item.getId(), 
				    item.getTitle(), 
				    item.getSolution(), 
				    item.getNoOfTimesWorked(), 
		    		item.getBug()));
		    return itemParent;
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
	public Fix updateItem(long parentId, long id, Fix postRequest) {
		if(!bugService.existsById(parentId)) {
		    throw new ResourceNotFoundException("Bug ID " + parentId + " not found");
		}
		
		// Set variables to update the item
		return fixRepository.findById(id).map(updateItem -> {
			updateItem.setId(postRequest.getId()); 
		    updateItem.setTitle(postRequest.getTitle()); 
		    updateItem.setSolution(postRequest.getSolution()); 
		    updateItem.setNoOfTimesWorked(postRequest.getNoOfTimesWorked()); 
		    updateItem.setBug(bugService.getOne(parentId));
		    fixRepository.save(updateItem); 
		    return updateItem;
		}).orElseThrow(() -> new ResourceNotFoundException("BugId " + id + " not found"));
	  }
	  
	/** 
	 * DELETE API for deleting a Bug object in the data store.
	 * @param parentId the ID of the Bug object to retrieve its Fixes for 
	 * @param id the ID of the Fix to be deleted 
	 * @return Server response indicating the item is deleted 
	 * @exception ResourceNotFoundException return the message that the item cannot be found  
	 */
	 public Fix deleteItem(long parentId, long id) {
	        return fixRepository.findByIdAndBugId(id, parentId).map(deleteItem -> {
	            fixRepository.delete(deleteItem);
	            return deleteItem;
	        }).orElseThrow(() -> new ResourceNotFoundException("Fix not found with ID " + id + 
	        		" and parent ID " + parentId));
	  }
}