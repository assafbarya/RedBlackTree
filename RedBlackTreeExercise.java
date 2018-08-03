package com.vogella.junit.first;


public class RedBlackTreeExercise<T extends Comparable<T>> {

	public static final boolean RED   = true;
	public static final boolean BLACK = false;

    protected RedBlackNode<T> root;
    protected RedBlackNode<T> sentinel;
    private int size;
    
    public RedBlackTreeExercise() {
    	
    	// initialize sentinel
    	sentinel = new RedBlackNode<T>(null);
    	sentinel.color = BLACK;
    	
    	root = sentinel;
    	size = 0; 
    }
    
    public int size() {
    	return size;
    }
	
    public void add(T s) {
    	RedBlackNode<T> x = new RedBlackNode<T>(s);
    	x.left  = sentinel;
    	x.right = sentinel;
    	
    	int sizeBefore 	= size;
    	root 			= addBST(root, x);

    	// make sure we only rearrange the tree if a new node has indeed been added
    	if (sizeBefore == size) return; // maybe there's some way to free the memory allocated for x? im not sure
    	x.color = RED;

    	// rearranging part
    	while ((x != root) && (x.parent.color == RED)) {
    		if (x.parent == x.parent.parent.left) {
    			/* If x's parent is a left, y is x's right 'uncle' */
    			RedBlackNode<T> y = x.parent.parent.right;
				if (y.color == RED) {
					/* case 1 - change the colors */
					x.parent.color 			= BLACK;
					y.color 					= BLACK;
					x.parent.parent.color 	= RED;
					/* Move x up the tree */
					x = x.parent.parent;
				}
				else {
					/* y is a black node */
					if (x == x.parent.right) {
						/* and x is to the right */ 
						/* case 2 - move x up and rotate */
						x = x.parent;
						leftRotate(this, x);
					}
					/* case 3 */
					x.parent.color 			= BLACK;
					x.parent.parent.color 	= RED;
					rightRotate(this, x.parent.parent);
    			}
    		}
    		else {
    			RedBlackNode<T> y = x.parent.parent.left;
				if (y.color == RED) {
					/* case 1 - change the colors */
					x.parent.color 			= BLACK;
					y.color 					= BLACK;
					x.parent.parent.color 	= RED;
					/* Move x up the tree */
					x = x.parent.parent;
				}
				else {
					/* y is a black node */
					if (x == x.parent.left) {
						/* and x is to the right */ 
						/* case 2 - move x up and rotate */
						x = x.parent;
						rightRotate(this, x);
					}
					/* case 3 */
					x.parent.color 			= BLACK;
					x.parent.parent.color 	= RED;
					leftRotate(this, x.parent.parent);
    			}
    		}
    	}
    	/* Color the root black */
    	root.color = BLACK;
    }

	// normal adding in a binary search tree
	private RedBlackNode<T> addBST( RedBlackNode<T> node, RedBlackNode<T> leaf) {
		if (node == sentinel) {
			size++;
			return leaf;
		}
		switch(node.value.compareTo(leaf.value)) {
		case 0:
			break;
		case 1:
			node.left 			= addBST(node.left, leaf);
			node.left.parent 	= node;
			node.leftKids++;
			break;
		case -1:
			node.right 			= addBST(node.right, leaf);
			node.right.parent 	= node;
			node.rightKids++;
			break;
		}
		return node;
	}
	
	private void updateKidCount(RedBlackNode<T> x) {
		x.leftKids 	= 0;
		x.rightKids = 0;
		if (x.left  != sentinel) x.leftKids  += 1 + x.left.leftKids  + x.left.rightKids;
		if (x.right != sentinel) x.rightKids += 1 + x.right.leftKids + x.right.rightKids;
	}
	
	public void leftRotate(RedBlackTreeExercise<T> tree, RedBlackNode<T> x) {
		RedBlackNode<T> y;
	    y = x.right;
	    /* Turn y's left sub-tree into x's right sub-tree */
	    x.right = y.left;
	    if (y.left != sentinel) y.left.parent = x;
	    /* y's new parent was x's parent */
	    y.parent = x.parent;
	    /* Set the parent to point to y instead of x */
	    /* First see whether we're at the root */
	    if (x.parent == null) tree.root = y;
	    else
	        if (x == x.parent.left)
	            /* x was on the left of its parent */
	            x.parent.left = y;
	        else
	            /* x must have been on the right */
	            x.parent.right = y;
	    /* Finally, put x on y's left */
	    y.left 		= x;
	    x.parent 	= y;
	    
	    // not really finally. update numbers of kids
	    updateKidCount(x);
	    updateKidCount(y);
	    }		
		
	public void rightRotate(RedBlackTreeExercise<T> tree, RedBlackNode<T> x) {
		RedBlackNode<T> y;
	    y = x.left;
	    x.left = y.right;
	    if (y.right != sentinel) y.right.parent = x;
	    y.parent = x.parent;
	    if (x.parent == null) tree.root = y;
	    else
	        if (x == x.parent.right)
	            x.parent.right = y;
	        else
	            x.parent.left = y;
	    y.right 	= x;
	    x.parent 	= y;
	    updateKidCount(x);
	    updateKidCount(y);
	    }
	
	public T get(int rank) {
		if (rank >= size)
			throw new IllegalArgumentException("rank should be less than tree size");
		return getFromNode(root, rank);
	}
	
	private T getFromNode( RedBlackNode<T> node, int rank) {
		if (rank < node.leftKids) return getFromNode(node.left, rank);
		if (rank == node.leftKids) return node.value;
		return getFromNode(node.right, rank - node.leftKids - 1);
	}
	
	public int rank(T s) {
		return rankFromNode(root, s);
	}
	
	private int rankFromNode(RedBlackNode<T> node, T s) {
		if (node.value.compareTo(s) == 0) return node.leftKids;
		if ( node.value.compareTo(s) == 1) {
			if (node.left == sentinel)
				return -1;
			return rankFromNode( node.left, s);
		}
		else {
			if (node.right == sentinel)
				return -1;
			int rightTreeRank = rankFromNode( node.right, s);
			if (-1 == rightTreeRank) 
				return -1;
			return node.leftKids + 1 + rightTreeRank;
		}
	}
}
