package com.vogella.junit.first;


public class RedBlackNode<T extends Comparable<T>> {

	public T 				value;
	public RedBlackNode<T> 	parent;
	public RedBlackNode<T> 	left;
	public RedBlackNode<T> 	right;
	public boolean 		   	color;
	public int				leftKids; // number of left kids
	public int				rightKids; // number of  right kids
	
	public RedBlackNode(T value) {
		parent     = null;
		left       = null;
		right  	   = null;
		color      = false;
		leftKids   = 0;
		rightKids  = 0;
		this.value = value;
	}
}
