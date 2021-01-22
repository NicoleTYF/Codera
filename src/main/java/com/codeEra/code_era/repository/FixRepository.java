package com.codeEra.code_era.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.codeEra.code_era.model.Fix;

/**
 * A repository for {@link com.codeEra.code_era.model.Fix} object. 
 * <p>
 * Extends <i>JPA Repository</i> to auto-generate CRUD functions. 
 * Consumed by {@link com.codeEra.code_era.controller.BugController}. 
 */
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@Repository
public interface FixRepository extends JpaRepository<Fix, Long> {
	
	/** 
	 * Find all items using inputs from the search bar . 
	 * @param bugId        the {@link com.codeEra.code_era.model.Bug#id} to search in the repository.  
	 * @param pageable     the list used to store retrieved records. 
	 * @return A list of Fix objects matching with the bug id.  
	 * */
	Page<Fix> findByBugId(long bugId, Pageable pageable);
	
	/** 
	 * Find all items using inputs from the search bar . 
	 * @param id           the {@link com.codeEra.code_era.model.Fix#id} to search in the repository.  
	 * @param bugId        the {@link com.codeEra.code_era.model.Bug#id} to search in the repository.  
	 * @return A list of Fix objects matching with both the id and its bug id.  
	 * */
    Optional<Fix> findByIdAndBugId(long id, long bugId);
}
