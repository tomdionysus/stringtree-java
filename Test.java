package TC.Utils.StringTree;

import java.util.*;
import junit.framework.TestCase;

public class Test extends TestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testContains() {
		StringTree<Integer> st = new StringTree<Integer>();
		
		st.add("Tom", 1);
		st.add("Hugh", 2);
		st.add("Duncan", 3);
		st.add("Tony", 4);
		st.add("Dunhill", 5);
		st.add("BacchusTheLiberator",6);
		st.add("BacchusTheTormentor",7);
		
		// Contains Test
		// - Positives
		assertEquals(st.contains("Tom"),true);
		assertEquals(st.contains("Hugh"),true);
		assertEquals(st.contains("Duncan"),true);
		assertEquals(st.contains("Tony"),true);
		assertEquals(st.contains("Dunhill"),true);
		assertEquals(st.contains("BacchusTheLiberator"),true);
		assertEquals(st.contains("BacchusTheTormentor"),true);
		// - Negatives
		assertEquals(st.contains("George"),false);
		assertEquals(st.contains("Tobias"),false);
	}

	public void testRemove() {
		
		StringTree<Integer> st = new StringTree<Integer>();
		
		st.add("Tom", 1);
		st.add("Hugh", 2);
		st.add("Duncan", 3);
		st.add("Tony", 4);
		st.add("Dunhill", 5);
		st.add("BacchusTheLiberator",6);
		st.add("BacchusTheTormentor",7);
		
		// Delete Test (Should not delete Tony)
		st.remove("Tom");
		st.remove("BacchusTheLiberator");
		// - Contains
		assertEquals(st.contains("Tom"),false);
		assertEquals(st.contains("Hugh"),true);
		assertEquals(st.contains("Duncan"),true);
		assertEquals(st.contains("Tony"),true);
		assertEquals(st.contains("Dunhill"),true);
		assertEquals(st.contains("BacchusTheLiberator"),false);
		assertEquals(st.contains("BacchusTheTormentor"),true);
		
		st.remove("Duncan");
		st.remove("Tony");
		st.remove("Dunhill");
		st.add("BacchusTheLiberator",6);
		st.remove("BacchusTheTormentor");
		
		assertEquals(st.contains("Tom"),false);
		assertEquals(st.contains("Hugh"),true);
		assertEquals(st.contains("Duncan"),false);
		assertEquals(st.contains("Tony"),false);
		assertEquals(st.contains("Dunhill"),false);
		assertEquals(st.contains("BacchusTheLiberator"),true);
		assertEquals(st.contains("BacchusTheTormentor"),false);
	}

	public void testGetException() {
		StringTree<Integer> st = new StringTree<Integer>();
		
		st.add("Tom", 1);
		st.add("Hugh", 2);
		st.add("Duncan", 3);
		st.add("Tony", 4);
		st.add("Dunhill", 5);
		st.add("BacchusTheLiberator",6);
		st.add("BacchusTheTormentor",7);
		
		// Get Exception Test (Should Generate Exception)
		try {
			st.get("George");
			fail();
		} catch (Exception e) {
			
		}
	}

	public void testLookup() {
		StringTree<Integer> st = new StringTree<Integer>();
		
		st.add("Tom", 1);
		st.add("Hugh", 2);
		st.add("Duncan", 3);
		st.add("Tony", 4);
		st.add("Dunhill", 5);
		st.add("BacchusTheLiberator",6);
		st.add("BacchusTheTormentor",7);
		
		// Lookup Tests
		try {
			assertEquals(st.get("Tom"),(Integer)1);
			assertEquals(st.get("Hugh"),(Integer)2);
			assertEquals(st.get("Duncan"),(Integer)3);
			assertEquals(st.get("Tony"),(Integer)4);
			assertEquals(st.get("Dunhill"),(Integer)5);
			assertEquals(st.get("BacchusTheLiberator"),(Integer)6);
			assertEquals(st.get("BacchusTheTormentor"),(Integer)7);
		} catch (Exception e) {
			fail();
		}
	}

	public void testOptimise() {
		StringTree<Integer> st = new StringTree<Integer>();
		
		st.add("Tom", 1);
		st.add("Hugh", 2);
		st.add("Duncan", 3);
		st.add("Tony", 4);
		st.add("Dunhill", 5);
		st.add("BacchusTheLiberator",6);
		st.add("BacchusTheTormentor",7);
		
		// Optimise and Rerun Lookup Tests
		st.Optimise();
		
		// Lookup Tests
		try {
			assertEquals(st.get("Tom"),(Integer)1);
			assertEquals(st.get("Hugh"),(Integer)2);
			assertEquals(st.get("Duncan"),(Integer)3);
			assertEquals(st.get("Tony"),(Integer)4);
			assertEquals(st.get("Dunhill"),(Integer)5);
			assertEquals(st.get("BacchusTheLiberator"),(Integer)6);
			assertEquals(st.get("BacchusTheTormentor"),(Integer)7);
		} catch (Exception e) {
			fail();
		}
	}

	public void testCentive() {
        StringTree<Integer> st = new StringTree<Integer>();
		
		st.add("Cafe", (Integer)1);
		st.add("Bar", (Integer)2);
		st.add("Cafe Bar", (Integer)3);
		
		String str = "The Loaded Hog Cafe Bar";
		
		List<StringTreeItem<Integer>> matches = st.matchAll(str);
		
		assertEquals((Integer)(matches.size()),(Integer)3);
		
		StringTreeItem<Integer> match;
		
		match = matches.get(0);
		assertEquals((Integer)(match.getOffset()),(Integer)15);
		assertEquals((String)(match.getStr()),"Cafe");
		assertEquals((Integer)(match.getValue()),(Integer)1);
		
		match = matches.get(1);
		assertEquals((Integer)(match.getOffset()),(Integer)15);
		assertEquals((String)(match.getStr()),"Cafe Bar");
		assertEquals((Integer)(match.getValue()),(Integer)3);
		
		match = matches.get(2);
		assertEquals((Integer)(match.getOffset()),(Integer)20);
		assertEquals((String)(match.getStr()),"Bar");
		assertEquals((Integer)(match.getValue()),(Integer)2);
	}
	
    public void testBarNative() {
        StringTree<Integer> st = new StringTree<Integer>();
        st.add("New World", (Integer)1);
        st.add("New Worl", (Integer)2);
        List<StringTreeItem<Integer>> matches = st.matchAll("New World");

		assertEquals((Integer)(matches.size()),(Integer)2);
		
		StringTreeItem<Integer> match;
		
		match = matches.get(0);
		assertEquals((Integer)(match.getOffset()),(Integer)0);
		assertEquals((String)(match.getStr()),"New Worl");
		assertEquals((Integer)(match.getValue()),(Integer)2);
		
		match = matches.get(1);
		assertEquals((Integer)(match.getOffset()),(Integer)0);
		assertEquals((String)(match.getStr()),"New World");
		assertEquals((Integer)(match.getValue()),(Integer)1);
    }
    
    public void testSubWords() {
        StringTree<Integer> st = new StringTree<Integer>();
        st.add("Balti House", (Integer)1);
        st.add("Bar", (Integer)2);
        List<StringTreeItem<Integer>> matches = st.matchAll("Bar");
        
		assertEquals((Integer)(matches.size()),(Integer)1);
		
		StringTreeItem<Integer> match;
		
		match = matches.get(0);
		assertEquals((Integer)(match.getOffset()),(Integer)0);
		assertEquals((String)(match.getStr()),"Bar");
		assertEquals((Integer)(match.getValue()),(Integer)2);
    }
    
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
