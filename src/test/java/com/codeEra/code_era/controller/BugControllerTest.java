/**
 * 
 */
package com.codeEra.code_era.controller;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import com.codeEra.code_era.model.Bug;
import com.codeEra.code_era.repository.BugRepository;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * /  ///  / /// / WORK-IN-PROGRESS / / / / / / 
 * JUnit testing on connection between Spring Boot and REST API
 * TODO: Integrate Spring MockMvc instead of using 
 */
class BugControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private BugRepository bugRepoMock;
	
	private Pageable pageable;
	
	@Test
    public void findAllByOrderByIdAsc() throws Exception {
		
        Bug testBug1 = new Bug();
        testBug1.setId(1); 
        testBug1.setUserId(0); 
        testBug1.setTitle("test title"); 
        testBug1.setCategory("Database Integration, Data Analysis, Dart"); 
        testBug1.setDescription("test description"); 
        testBug1.setTags("oracle tag, sql tag");
        testBug1.setProgammingLanguage("Java"); 
 
        Bug testBug2 = new Bug();
        testBug2.setId(2); 
        testBug2.setUserId(3); 
        testBug2.setTitle("test title 2"); 
        testBug2.setCategory("Networking, React/Angular/Vue, AWS"); 
        testBug2.setDescription("test description 2"); 
        testBug2.setTags("test tag 1, test tag , test tag 3");
        testBug2.setProgammingLanguage("Java"); 
 
        when(bugRepoMock.findAllByOrderByIdDesc(pageable).getContent()).
        	thenReturn(Arrays.asList(testBug1, testBug2));
        
        //Retrieve GET API's array
        mockMvc.perform(get("/"))
        		// Mocking network status
                .andExpect(status().isOk())
                // Enter the mapped URL which has the GET API   
                .andExpect(view().name("code_era/bugs/all"))
                // Retrieved data received by frontend 
                .andExpect(forwardedUrl("/code_era/src/views/ListItemView.jsx")) 
                // Result array size
                .andExpect(model().attribute("bugs", hasSize(2)))
                // Result array contents 
                .andExpect(model().attribute("bugs", hasItem(
                        allOf(
                            // 1st tested Bug
                            hasProperty("id", is(2L)),
                            hasProperty("userId", is(3)),
                            hasProperty("title", is("test title 2")), 
                            hasProperty("category", is("Database Integration, Data Analysis, Dart")),
                            hasProperty("description", is("test description")),
                            hasProperty("tags", is("oracle tag, sql tag")),
                            hasProperty("programmingLanguage", is("Java"))
                        )
                ))).andExpect(model().attribute("bugs", hasItem(
                        allOf(
                        	// 2nd tested Bug
                    		hasProperty("id", is(1L)),
                            hasProperty("userId", is(0)),
                            hasProperty("title", is("test title")), 
                            hasProperty("category", is("Networking, React/Angular/Vue, AWS")),
                            hasProperty("description", is("test description 2")),
                            hasProperty("tags", is("test tag 1, test tag , test tag 3")),
                            hasProperty("programmingLanguage", is("Java"))
                        )
                )));
 
        verify(bugRepoMock, times(1)).findAll();
        verifyNoMoreInteractions(bugRepoMock);
    }

}
