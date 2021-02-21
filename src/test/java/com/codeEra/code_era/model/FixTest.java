package com.codeEra.code_era.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit testing for Fix class
 */
class FixTest {

	Fix testFix; 
	
	Bug testBug;
	
	@BeforeEach
	void setup() {
		testBug = new Bug("test categories 1, test categories 2", 
				"test programming language", "test tag 1, test tag 2", 
				"test title", 1, 2, "test description");
		testFix = new Fix(1, "test title", "test solution", 4, testBug);
	}

	/**
	 * Test {@link com.codeEra.code_era.model.Fix#Fix()} constructors.
	 */
	@SuppressWarnings("unused")
	@Test
	void testFix() {
		testFix = new Fix();
		
		Assertions.assertTrue(testFix instanceof Fix);
		
		Assertions.assertEquals(0, testFix.getId()); 
		Assertions.assertNull(testFix.getTitle()); 
		Assertions.assertNull(testFix.getSolution()); 
		Assertions.assertEquals(0, testFix.getNoOfTimesWorked()); 
		Assertions.assertNull(testFix.getBug()); 
		
		testFix = new Fix("test title", "test solution", 4, new Bug());
	}
	
	/**
	 * Test method for {@link com.codeEra.code_era.model.Fix#Fix()}.
	 */
	@Test
	void testGetterSetters() {
		Assertions.assertEquals(1, testFix.getId());
		testFix.setId(Long.MAX_VALUE);
		Assertions.assertEquals(Long.MAX_VALUE, testFix.getId()); 
		
		Assertions.assertEquals("test title", testFix.getTitle());
		testFix.setTitle("");
		Assertions.assertEquals("", testFix.getTitle()); 
		testFix.setTitle(null);
		Assertions.assertNull(testFix.getTitle()); 
		
		Assertions.assertEquals("test solution", testFix.getSolution());
		String testStr = "<p>When I insert this record in H2 database: </p><p>I got the sql script of Scott schema, but when i try to run the query:</p><code>insert into Fix values(1, ORA-65096: invalid common user or role name in oracle, </p><p>\r\n"
				+ "              (Data Analysis, Database Integration), </p><p>\r\n"
				+ "               I just installed Oracle, and it was missing the Scott schema. </p><p>So i am trying to generate it myself.</p><p>I got the sql script of Scott schema, but when i try to run the query:</p><code>create user Scott identified by tiger;</p> "
				+ "				 </code><p>it displays the following error:</p><blockquote>ORA-65096: "
				+ "				 invalid common user or role name in oracle.</blockquote><p>Basically it is not allowing me to create a user <b>Scott</b>.</p>, \r\n"
				+ "				  (1), Java, (oracle, oracle-12c), 1);</code><p>it displays the following error:</p><blockquote>ORA-65096: invalid common user or role name in oracle.</blockquote><p>Basically it is not allowing me to create a user <b>Scott</b>.</p>"; 
		testFix.setSolution(testStr);
		Assertions.assertEquals(testStr, testFix.getSolution()); 
		testFix.setSolution(null);
		Assertions.assertNull(testFix.getSolution());
		
		Assertions.assertEquals(4, testFix.getNoOfTimesWorked());
		testFix.setNoOfTimesWorked(Integer.MAX_VALUE);
		Assertions.assertEquals(Integer.MAX_VALUE, testFix.getNoOfTimesWorked()); 
		
		Assertions.assertTrue(testBug.equals(testFix.getBug())); 
		Bug b = testBug; 
		b.setTitle("test bug title");
		testFix.setBug(b);
		Assertions.assertEquals(b, testFix.getBug()); 
		testFix.setBug(null);
		Assertions.assertNull(testFix.getBug()); 
	}
	
	/**
	 * Test method for {@link com.codeEra.code_era.model.Fix#equals()}.
	 */
	@Test
	void testEquals() { 
		Assertions.assertTrue(testFix.equals(testFix)); 
		
		Bug testBug = new Bug();
		Assertions.assertFalse(testFix.equals(testBug)); 
		
		Fix f = new Fix();
		
		Fix n = new Fix(1, "test title", "test solution", 4, new Bug()); 
		f.setId(9); 
		Assertions.assertFalse(testFix.equals(f)); 
		
		f = n; 
		f.setTitle(null); 
		Assertions.assertFalse(testFix.equals(f));  

		f = n; 
		f.setSolution(null); 
		Assertions.assertFalse(testFix.equals(f)); 
		
		f = n; 
		f.setNoOfTimesWorked(0); 
		Assertions.assertFalse(testFix.equals(f));  
		
		f = n; 
		f.setSolution(null);
		Assertions.assertFalse(testFix.equals(f)); 
		
		f = n; 
		f.setBug(null); 
		Assertions.assertFalse(testFix.equals(f)); 
	}
	
	/**
	 * Test method for {@link com.codeEra.code_era.model.Fix#toString()}.
	 */
	@Test
	void testToString() { 
		String str = "FIX DETAILS: \n" + "ID: 1\n TITLE: test title\n SOLUTION: " + 
				  "test solution\n # OF TIMES WORKED: 4\n BUG: " + testFix.getBug(); 
		Assertions.assertEquals(str, testFix.toString());
	}

}
