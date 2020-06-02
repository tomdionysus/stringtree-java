package TC.Utils.StringTree;

import java.util.*;

public class StringTree<T> {

	private StringTreeNode<T> root;
	
	public void setRoot(StringTreeNode<T> root) {
		this.root = root;
	}

	public StringTreeNode<T> getRoot() {
		return root;
	}

	// Add the given string association to the StringTree
	public void add(String str, T value) {
		int index = 0;
		if (getRoot() == null) setRoot(new StringTreeNode<T>(str.charAt(0)));
		StringTreeNode<T> current = getRoot();
		
		while (true) {
			current = current.AddHorizontal(str.charAt(index++));
			if (current.getDown() == null) {
				// Roll out the rest of thing string, straight down
				while (index<str.length()) {
					StringTreeNode<T> newnode = new StringTreeNode<T>(str.charAt(index++));
					current.setDown(newnode);
					current = newnode;
				}
				break;
			} else {
				if (index==str.length()) break;
				current = current.getDown();
			}
		}
		current.setValue(value);
	}
	
	// Get the associated value of the given string if it exists, or
	// throw an exception if it doesn't
	public T get(String str) throws Exception {
		StringTreeNode<T> current = findLastNode(str);
		if (current==null || !current.getHasvalue()) {
			throw new Exception("Not Found.");
		} else
			return current.getValue();
	}
	
	// Remove the given string association on the StringTree, return 
	// true if the string was found and removed
	public boolean remove(String str) {
		StringTreeNode<T> current = findLastNode(str);
		if (current!=null) {
			current.clearValue();
			root.pruneDown();
			root.pruneAcross();
			return true;
		}
		return false;
	}
	
	// Find the terminal node for a given string in the StringTree,
	// or null if it does not exist.
	public StringTreeNode<T> findLastNode(String str) {
		int index = 0;
		StringTreeNode<T> current = getRoot().FindHorizontal(str.charAt(index++));
		while (index<str.length() && current!=null) {
			current = current.getDown();
			if (current == null) return null;
			current = current.FindHorizontal(str.charAt(index++));
		}
		return current;
	}
	
	// Return true if the given string exists in the StringTree
	public boolean contains(String str) {
		StringTreeNode<T> node = findLastNode(str);
		if (node==null) return false;
		return node.getHasvalue();
	}
	
	// Optimise the entire tree
	public void Optimise() {
		if (getRoot()==null) return;
		setRoot(getRoot().Optimise(true));
	}
	
	// Find all matches in a string
	public List<StringTreeItem<T>> matchAll(String str) {
		List<StringTreeItem<T>> matches = new ArrayList<StringTreeItem<T>>();
		if (root==null) return matches;
		int cp = 0;
		while (cp<str.length()) {
			findinstr(str,cp++,matches);
		}
		return matches;
	}
	
	// Internal implementation of above method
	protected void findinstr(String str, int offset, List<StringTreeItem<T>> matches) {
		int cp = offset;
		StringTreeNode<T> cur;
		cur = null;
		while (cp<str.length()) {
			if (cur==null) cur=root; else cur = cur.getDown();
			if (cur==null) return;
			char c = str.charAt(cp++);
			cur = cur.FindHorizontal(c);
			if (cur==null) return;
			if (cur.getHasvalue()) {
				try {
					matches.add(new StringTreeItem<T>(offset, str.substring(offset,cp), cur.getValue()));
				} catch (Exception e) {
					//This should never happen.
				}
			}
		}

	}
	
	public void clear() {
		root = null;
	}
	
}
