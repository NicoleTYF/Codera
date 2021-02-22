package com.codeEra.code_era.service;

// Utilities
import java.util.List;
import java.util.Optional;

// Spring Data
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
// Other Classes
import com.codeEra.code_era.model.Bug;
import com.codeEra.code_era.repository.BugRepository;
import com.codeEra.code_era.exception.ResourceNotFoundException;


/**
 * Handles additional logic behind {@link com.codeEra.code_era.model.BugController} REST API methods.
 */
@Service
@Transactional
public class BugService {

	/** The data store of the Bug table. */
    @Autowired
    private BugRepository bugRepository;

    /** 
     * GET API for retrieving all Bugs from the data store. 
     * @param pageable the list to store the retrieved Bugs 
     * @return all the Bugs in the data store 
     */
    public List<Bug> findAllByOrderByIdDesc (Pageable pageable) {
	    return this.bugRepository.findAllByOrderByIdDesc(pageable).getContent();
    }
  
    /** 
     * GET API for retrieving a Bug by {@link com.codeEra.code_era.model.Bug#id}.
     * @param id the ID of the Bug to be retrieved 
     * @return the Bug found in the data store with the ID 
     */
    public Bug getOne (long id) {
	    return this.bugRepository.getOne(id);
    }
  
    /** 
     * POST API for creating a Bug object in the data store.
     * @param item the input item to bee created 
     * @return The item created 
     */
    public Bug create (Bug item) {
        return bugRepository.saveAndFlush(new Bug(item.getCategory(), item.getProgammingLanguage(), 
        		  item.getTags(), item.getTitle(), item.getUserId(), bugRepository.count() + 1,
        		  item.getDescription()));
    }
  
    /** 
     * PUT API for updating a Bug object in the data store. 
     * @param id the ID of the Bug to be updated 
     * @param postRequest the input item from the frontend 
     * @exception ResourceNotFoundException return the message that the item cannot be found  
     */
    public void updateAndSave (long id, Bug postRequest) {
	  	// Set variables to create the item
        bugRepository.findById(id).map(item -> {
           item.setId(postRequest.getId());
           item.setTitle(postRequest.getTitle()); 
           item.setCategory(postRequest.getCategory()); 
           item.setProgammingLanguage(postRequest.getProgammingLanguage()); 
           item.setDescription(postRequest.getDescription()); 
           item.setTags(postRequest.getTags()); 
           item.setFix(postRequest.getFix()); 
           item.setUserId(postRequest.getUserId());
           bugRepository.save(item); 
           return item;
        }).orElseThrow(() -> new ResourceNotFoundException("BugId " + id + " not found"));
     }
  
	/** 
	 * DELETE API for deleting a Bug object in the data store.
     * @param id the ID of the Bug to be deleted 
     * @return Server response indicating the item is deleted 
     * @exception ResourceNotFoundException return the message that the item cannot be found  
     */
	public ResponseEntity<?> deleteItem (long id) {
		 return bugRepository.findById(id).map(item -> {
	            bugRepository.delete(item);
	            return ResponseEntity.ok().build();
	     }).orElseThrow(() -> new ResourceNotFoundException("BugId " + id + " not found")); 
	}
	
    public Optional<Bug> findById (long id) {
	    return this.bugRepository.findById(id);
    }
	
	public boolean existsById (long id) {
		 return bugRepository.existsById(id); 
	}
	
}