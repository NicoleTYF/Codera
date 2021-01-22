package com.codeEra.code_era.controller;

// Communicating with the server
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

//Spring boot data handling 
import java.util.List;
import org.springframework.data.domain.Pageable;
//Spring boot annotations
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
//URI builder
import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//File references
import com.codeEra.code_era.model.Bug;
import com.codeEra.code_era.repository.BugRepository;
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
	  private BugRepository bugRepository;
	  
	  /** 
	   * GET API for retrieving all Bugs from the data store. 
	   * @param username the username of this author 
	   * @param pageable the list to store the retrieved Bugs 
	   * @return all the Bugs in the data store 
	   */
	  @GetMapping("/{username}/bugs/")
	  public List<Bug> getAllItems (@PathVariable String username, Pageable pageable) {
		  return this.bugRepository.findAllByOrderByIdAsc(pageable).getContent();
	  }
	  
	  /** 
	   * GET API for retrieving a Bug by {@link com.codeEra.code_era.model.Bug#id}.
	   * @param username the username of the author 
	   * @param id the ID of the Bug to be retrieved 
	   * @return the Bug found in the data store with the ID 
	   */
	  @GetMapping("/{username}/bugs/{id}")
      public Bug getItem (@PathVariable String username, @PathVariable long id) {
		  return this.bugRepository.getOne(id);
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
		      Bug createdItem = bugRepository
		          .saveAndFlush(new Bug(item.getCategory(), item.getProgammingLanguage(), 
		        		  item.getTags(), item.getTitle(), item.getUserId(), 5,
		        		  item.getDescription()));
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
	        return bugRepository.findById(id).map(item -> {
	            item.setId(postRequest.getId());
	            item.setTitle(postRequest.getTitle()); 
	            item.setCategory(postRequest.getCategory()); 
	            item.setProgammingLanguage(postRequest.getProgammingLanguage()); 
	            item.setDescription(postRequest.getDescription()); 
	            item.setTags(postRequest.getTags()); 
	            item.setFix(postRequest.getFix()); 
	            item.setUserId(postRequest.getUserId());
	            bugRepository.save(item); 
	            return new ResponseEntity<Bug>(item, HttpStatus.OK);
	        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + id + " not found")); 
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
		  return bugRepository.findById(id).map(bug -> {
	            bugRepository.delete(bug);
	            return ResponseEntity.ok().build();
	        }).orElseThrow(() -> new ResourceNotFoundException("BugId " + id + " not found")); 
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
		 * this.bugRepository.findByCategories(category, pageable).getContent(); }
		 */
}
