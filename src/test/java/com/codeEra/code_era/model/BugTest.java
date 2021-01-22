/**
 * 
 */
package com.codeEra.code_era.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit testing for Bug class 
 */
class BugTest {
	
	Bug testBug;
	 
	@BeforeEach
	void setup() {
		testBug = new Bug("test categories 1, test categories 2", 
				"test programming language", "test tag 1, test tag 2", 
				"test title", 1, 2, "test description");
	}

	/**
	 * Test {@link com.codeEra.code_era.model.Bug#Bug()} constructors.
	 */
	@Test
	void testBug() {
		testBug = new Bug();
		
		Assertions.assertTrue(testBug instanceof Bug);
		
		Assertions.assertEquals(0, testBug.getId()); 
		Assertions.assertEquals(0, testBug.getUserId()); 
		Assertions.assertNull(testBug.getTitle()); 
		Assertions.assertNull(testBug.getProgammingLanguage()); 
		Assertions.assertNull(testBug.getDescription()); 
		Assertions.assertNull(testBug.getCategory()); 
		Assertions.assertNull(testBug.getTags()); 
		Assertions.assertNull(testBug.getFix()); 
		
		testBug = new Bug("test categories 1, test categories 2", 
				"test programming language", "test tag 1, test tag 2", 
				"test title", 2, "test description");
	}
	
	/**
	 * Test method for {@link com.codeEra.code_era.model.Bug#Bug()}.
	 */
	@Test
	void testGetterSetters() {
		Assertions.assertEquals(2, testBug.getId());
		testBug.setId(Long.MAX_VALUE);
		Assertions.assertEquals(Long.MAX_VALUE, testBug.getId()); 
		
		Assertions.assertEquals(1, testBug.getUserId());
		testBug.setUserId(Long.MAX_VALUE);
		Assertions.assertTrue(testBug.getUserId() == Long.MAX_VALUE); 
		
		Assertions.assertEquals("test title", testBug.getTitle());
		testBug.setTitle("");
		Assertions.assertEquals("", testBug.getTitle()); 
		testBug.setTitle(null);
		Assertions.assertNull(testBug.getTitle()); 
		
		Assertions.assertEquals("test programming language", testBug.getProgammingLanguage());
		testBug.setProgammingLanguage("C++"); 
		Assertions.assertEquals("C++", testBug.getProgammingLanguage()); 
		testBug.setProgammingLanguage(null);
		Assertions.assertNull(testBug.getProgammingLanguage()); 
		
		Assertions.assertEquals("test description", testBug.getDescription());
		String testStr = "<p>When I insert this record in H2 database: </p><p>I got the sql script of Scott schema, but when i try to run the query:</p><code>insert into Bug values(1, ORA-65096: invalid common user or role name in oracle, </p><p>\r\n"
				+ "              (Data Analysis, Database Integration), </p><p>\r\n"
				+ "               I just installed Oracle, and it was missing the Scott schema. </p><p>So i am trying to generate it myself.</p><p>I got the sql script of Scott schema, but when i try to run the query:</p><code>create user Scott identified by tiger;</p> "
				+ "				 </code><p>it displays the following error:</p><blockquote>ORA-65096: "
				+ "				 invalid common user or role name in oracle.</blockquote><p>Basically it is not allowing me to create a user <b>Scott</b>.</p>, \r\n"
				+ "				  (1), Java, (oracle, oracle-12c), 1);</code><p>it displays the following error:</p><blockquote>ORA-65096: invalid common user or role name in oracle.</blockquote><p>Basically it is not allowing me to create a user <b>Scott</b>.</p>"; 
		testBug.setDescription(testStr);
		Assertions.assertEquals(testStr, testBug.getDescription()); 
		testBug.setDescription(null);
		Assertions.assertNull(testBug.getDescription());
		
		Assertions.assertEquals("test categories 1, test categories 2", testBug.getCategory());
		testBug.setCategory("");
		Assertions.assertEquals("", testBug.getCategory()); 
		testBug.setCategory(null);
		Assertions.assertNull(testBug.getCategory()); 
		
		Assertions.assertEquals("test tag 1, test tag 2", testBug.getTags());
		testBug.setTags("");
		Assertions.assertEquals("", testBug.getTags()); 
		testBug.setTags(null);
		Assertions.assertNull(testBug.getTags()); 
	}
	
	/**
	 * Test method for {@link com.codeEra.code_era.model.Bug#equals()}.
	 */
	@Test
	void testEquals() { 
		Assertions.assertTrue(testBug.equals(testBug)); 
		
		Fix testFix = new Fix();
		Assertions.assertFalse(testBug.equals(testFix)); 
		
		Bug b = new Bug();
		
		Bug n = new Bug("test categories 1, test categories 2", 
				"test programming language", "test tag 1, test tag 2", 
				"test title", 1, 2, "test description");
		b = n;
		b.setId(9); 
		Assertions.assertFalse(testBug.equals(b)); 
				
		b = n; 
		b.setUserId(0);  
		Assertions.assertFalse(testBug.equals(b)); 
		
		b = n; 
		b.setTitle(null); 
		Assertions.assertFalse(testBug.equals(b));  

		b = n; 
		b.setProgammingLanguage(null); 
		Assertions.assertFalse(testBug.equals(b)); 
		
		b = n; 
		b.setCategory(null); 
		Assertions.assertFalse(testBug.equals(b));  
		
		b = n; 
		b.setTags(null); 
		Assertions.assertFalse(testBug.equals(b)); 
		
		b = n; 
		b.setFix(null); 
		Assertions.assertFalse(testBug.equals(b)); 
		
		b = n; 
		Set<Fix> f = new HashSet<Fix>();
		f.add(new Fix());
		b.setFix(f); 
		Assertions.assertFalse(testBug.equals(b)); 
	}
	
	/**
	 * Test method for {@link com.codeEra.code_era.model.Bug#toString()}.
	 */
	@Test
	void testToString() { 
		String str = "BUG DETAILS: \nID: 2\n TITLE: test title\n LANGUAGE USED: test programming language" + 
				"\n CATEGORY: test categories 1, test categories 2" + 
				"\n DESCRIPTION: test description\n TAGS: test tag 1, test tag 2\n FIX: " + testBug.getFix(); 
		Assertions.assertEquals(str, testBug.toString());
	}

}
