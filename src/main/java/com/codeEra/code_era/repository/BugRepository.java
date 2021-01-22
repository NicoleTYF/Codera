package com.codeEra.code_era.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.codeEra.code_era.model.Bug;

/**
 * A repository for {@link com.codeEra.code_era.model.Bug} object. 
 * <p>
 * Extends <i>JPA Repository</i> to auto-generate CRUD functions. 
 * Consumed by {@link com.codeEra.code_era.controller.BugController}. 
 */
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
	
	/** 
	 * Find all items using inputs from the search bar . 
	 * @param pageable  the list used to store retrieved records.  
	 * @return a list of Bugs with matched titles  
	 */
	Page<Bug> findAllByOrderByIdAsc(Pageable pageable);
	
	/** 
	 * Find all items using inputs from the search bar . 
	 * @param searchTerm   the {@link com.codeEra.code_era.model.Bug#title} to search in the repository.  
	 * @param pageable     the list used to store retrieved records. 
	 * @return a list of Bugs with matched titles  
	 */
	Page<Bug> findByTitle(String searchTerm, Pageable pageable);
	
	/** 
	 * Find all items using inputs from the <em>"Programming language" checkbox filter</em>. 
	 * @param progLang     the {@link com.codeEra.code_era.model.Bug#progLang} to search in the repository.  
	 * @param pageable     the list used to store retrieved records. 
	 * @return a list of Bugs with matched programming language   
	 */
	Page<Bug> findByProgLang(String progLang, Pageable pageable);
	
	/** 
	 * Find all items using inputs from the <em>"Categories" checkbox filter</em>. 
	 * @param category     the {@link com.codeEra.code_era.model.Bug#categories} to search in the repository.  
	 * @param pageable     the list used to store retrieved records. 
	 * @return a list of Bugs with matched categories  
	 */
	Page<Bug> findByCategoriesContaining(String category, Pageable pageable);
	
	
}
