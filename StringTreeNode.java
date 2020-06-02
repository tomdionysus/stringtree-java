package TC.Utils.StringTree;

public class StringTreeNode<T> {

	private StringTreeNode<T> left;
	private StringTreeNode<T> right;
	private StringTreeNode<T> down;
	
	private T value;
	private char chr;
	private boolean hasvalue;
	
	public StringTreeNode<T> getLeft() {
		return left;
	}
	public void setLeft(StringTreeNode<T> left) {
		this.left = left;
	}
	public StringTreeNode<T> getRight() {
		return right;
	}
	public void setRight(StringTreeNode<T> right) {
		this.right = right;
	}
	public StringTreeNode<T> getDown() {
		return down;
	}
	public void setDown(StringTreeNode<T> down) {
		this.down = down;
	}
	public T getValue() throws Exception {
		if (!hasvalue) {
			throw new Exception();
		} else
			return value;
	}
	public void setValue(T value) {
		this.value = value;
		this.hasvalue = true;
	}
	public boolean clearValue() {
		this.hasvalue = false;
		return (left==null && right==null && down==null);
	}	
	public char getChr() {
		return chr;
	}
	public void setChr(char chr) {
		this.chr = chr;
	}
	public boolean getHasvalue() {
		return hasvalue;
	}
	
	public StringTreeNode(char pchr) {
		setChr(pchr);
	}
	public StringTreeNode(char pchr, T pvalue) {
		setChr(chr);
		setValue(pvalue);
	}
	
	// Add a given node to this StringTree level (horizontal)
	public void AddHorizontal(StringTreeNode<T> node) {
		if (node.chr == chr) return;
		if (this.chr > node.chr) {
			if (left==null) {
				left = node;
			} else {
				left.AddHorizontal(node);
			}
		} else {
			if (right==null) {
				right = node;
			} else {
				right.AddHorizontal(node);
			}
		}
	}
	
	// Add a new node with the given character to this StringTree level,
	// or return the node for this character if it already exists.
	public StringTreeNode<T> AddHorizontal(char chr) {
		if (this.chr == chr) return this;
		if (this.chr > chr) {
			if (left==null) {
				left = new StringTreeNode<T>(chr);
				return left;
			} else {
				return left.AddHorizontal(chr);
			}
		} else {
			if (right==null) {
				right =  new StringTreeNode<T>(chr);
				return right;
			} else {
				return right.AddHorizontal(chr);
			}
		}
	}
	
	// Add the given node to the next level of the StringTree, creating a new
	// level if one does not exist.
	public void AddVertical(StringTreeNode<T> node) {
		if (down==null) {
			down = node;
		} else {
			down.AddHorizontal(node);
		}
	}
	
	// Find the given character at this StringTree level
	public StringTreeNode<T> FindHorizontal(char c) {
		if (chr == c) return this;
		if (chr>c) {
			if (left==null) return null;
			return left.FindHorizontal(c);
		} else {
			if (right==null) return null;
			return right.FindHorizontal(c);
		}
	}
	
	// Optimise this StringTree level, return the new root
	public StringTreeNode<T> Optimise(boolean recursive) {
		int c = (CountRight() - CountLeft()) / 2;
		StringTreeNode<T> newroot = this;
		while (c!=0) {
			if (c<0) {
				// Rotate Left
				StringTreeNode<T> tmp = newroot.left;
				newroot.left = null;
				tmp.AddHorizontal(newroot);
				newroot = tmp;
				c++;
			} else {
				// Rotate Right
				StringTreeNode<T> tmp = newroot.right;
				newroot.right = null;
				tmp.AddHorizontal(newroot);
				newroot = tmp;
				c--;
			}
		}
		if (recursive) {
			if (newroot.left!=null) newroot.left = newroot.left.Optimise(true);
			if (newroot.right!=null) newroot.right = newroot.right.Optimise(true);
			if (newroot.down!=null) newroot.down = newroot.down.Optimise(true);
		}
		return newroot;
	}
	
	// Remove child empty leaf nodes vertically
	public boolean pruneDown() {
		if (left!=null) left.pruneDown();
		if (right!=null) right.pruneDown();
		
		if (down!=null) {
			if (down.pruneDown()) {
				StringTreeNode<T> newdown;
				if (down.left!=null) {
					newdown = down.left;
					if (newdown.right!=null) newdown.AddHorizontal(down.right);
					down = newdown;
					down.pruneAcross();
				} else {
					if (down.right!=null) {
						down = down.right;
						down.pruneAcross();
					} else {
						down = null;			
					}
				}
			}
		}		
		return (down==null && !getHasvalue());
	}
		
	public boolean pruneAcross() {
		if (left!=null) {
			if (left.pruneAcross()) {
				StringTreeNode<T> node = left;
				left = null;
				if (node.left!=null) AddHorizontal(node.left);
				if (node.right!=null) AddHorizontal(node.right);
			}
		}
		if (right!=null) {
			if (right.pruneAcross()) {
				StringTreeNode<T> node = right;
				right = null;
				if (node.left!=null) AddHorizontal(node.left);
				if (node.right!=null) AddHorizontal(node.right);
			}
		}
		
		return (down==null && !getHasvalue());
	}

	private int CountLeft() {
		int c = -1;
		StringTreeNode<T> current = this;
		while (current!=null) {
			current = current.left;
			c++;
		}
		return c;
	}
	private int CountRight() {
		int c = -1;
		StringTreeNode<T> current = this;
		while (current!=null) {
			current = current.right;
			c++;
		}
		return c;	
	}
}
