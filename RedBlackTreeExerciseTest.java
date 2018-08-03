/**
 * 
 */
package com.vogella.junit.first;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.*;  
import static org.hamcrest.CoreMatchers.*;

/**
 * @author assaf
 *
 */
public class RedBlackTreeExerciseTest {
	
	private class Orderer<T extends Comparable<T>> {
		private List<T> dfs(RedBlackTreeExercise<T> tree) {
			return dfsNode(tree.root);
		}
		
		private List<T> dfsNode(RedBlackNode<T> node) {
			List<T> l = new ArrayList<T>();
			if (node.value == null) return l;
			if (node.left != null) l.addAll(dfsNode(node.left));
			l.add(node.value);
			if (node.right != null) l.addAll(dfsNode(node.right));
			return l;
		}
		
		private List<T> bfs(RedBlackTreeExercise<T> tree) {
			List<T> l = new ArrayList<T>();
			Queue<RedBlackNode<T>> queue = new LinkedList<RedBlackNode<T>>();
			queue.clear();
			queue.add(tree.root);
		    while(!queue.isEmpty()){
		    	RedBlackNode<T> node = queue.remove();
		        if (node.value != null) l.add(node.value);
		        if(node.left.value != null) queue.add(node.left);
		        if(node.right.value != null) queue.add(node.right);
		    }
		    return l;
		}
	}

	
	/**
	 * Test method for {@link com.vogella.junit.first.RedBlackTreeExercise#add(java.lang.Comparable)}.
	 */
	@Test
	public void testAdd() {
		RedBlackTreeExercise<Integer> rbt = new RedBlackTreeExercise<Integer>();
		rbt.add(2);
		rbt.add(1);
		rbt.add(5);
		Orderer<Integer> orderer 	= new Orderer<Integer>();
		List<Integer> dfsRes 		= orderer.dfs(rbt);
		List<Integer> expected 		= List.of(1,2,5);
		assertThat(dfsRes, is(expected));
		assertEquals(3, rbt.size());
	}
	
	@Test
	public void testLeftRotate() {
		RedBlackTreeExercise<Integer> o = new RedBlackTreeExercise<Integer>();
		o.add(3);
		o.add(2);
		o.add(5);
		o.add(4);
		o.add(6);
		Orderer<Integer> d = new Orderer<Integer>();
		assertThat(d.dfs(o), is(List.of(2,3,4,5,6)));
		assertThat(d.bfs(o), is(List.of(3,2,5,4,6)));
		
		o.leftRotate(o, o.root);
		assertThat(d.dfs(o), is(List.of(2,3,4,5,6)));
		assertThat(d.bfs(o), is(List.of(5,3,6,2,4)));
		assertEquals(5, o.size());
	}

	@Test
	public void testRightRotate() {
		RedBlackTreeExercise<Integer> o = new RedBlackTreeExercise<Integer>();
		o.add(5);
		o.add(3);
		o.add(6);
		o.add(2);
		o.add(4);
		Orderer<Integer> d = new Orderer<Integer>();
		assertThat(d.dfs(o), is(List.of(2,3,4,5,6)));
		assertThat(d.bfs(o), is(List.of(5,3,6,2,4)));
		
		o.rightRotate(o, o.root);
		assertThat(d.dfs(o), is(List.of(2,3,4,5,6)));
		assertThat(d.bfs(o), is(List.of(3,2,5,4,6)));
		assertEquals(5, o.size());
	}
		
	@Test
	public void testAddRebalance() {
		RedBlackTreeExercise<Integer> rbt = new RedBlackTreeExercise<Integer>();
		rbt.add(11);
		rbt.add(2);
		rbt.add(14);
		rbt.add(1);
		rbt.add(7);
		rbt.add(15);
		rbt.add(5);
		rbt.add(8);
		rbt.add(4);
		
		rbt.add(7); // add 7 twice, to make sure we handle duplicate values well
		Orderer<Integer> orderer = new Orderer<Integer>();
		assertThat(orderer.dfs(rbt), is(List.of(1, 2, 4, 5, 7, 8, 11, 14, 15)));
		assertThat(orderer.bfs(rbt), is(List.of(7, 2, 11, 1, 5, 8, 14, 4, 15)));
		assertEquals(9, rbt.size());
	}
	
	@Test
	public void testGet() {
		RedBlackTreeExercise<Integer> rbt = new RedBlackTreeExercise<Integer>();
		rbt.add(11);
		rbt.add(2);
		rbt.add(14);
		rbt.add(1);
		rbt.add(7);
		rbt.add(15);
		rbt.add(5);
		rbt.add(8);
		rbt.add(4);
		Integer[] ranks = new Integer[] {1, 2, 4, 5, 7, 8, 11, 14, 15};
		for (int idx=0;idx<ranks.length;++idx)
			assertEquals(rbt.get(idx), ranks[idx]);
	}
	
	@Test
	public void testRank() {
		RedBlackTreeExercise<Integer> rbt = new RedBlackTreeExercise<Integer>();
		rbt.add(11);
		rbt.add(2);
		rbt.add(14);
		rbt.add(1);
		rbt.add(7);
		rbt.add(15);
		rbt.add(5);
		rbt.add(8);
		rbt.add(4);
		Integer[] ranks = new Integer[] {1, 2, 4, 5, 7, 8, 11, 14, 15};
		for (int idx=0;idx<ranks.length;++idx)
			assertEquals(rbt.rank(ranks[idx]), idx);
		
		Integer[] nonExisting = new Integer[] {-1, 0, 3, 6, 9, 10, 12, 13, 16, 17};
		for (int idx=0;idx<nonExisting.length;++idx)
			assertEquals(rbt.rank(nonExisting[idx]), -1);
	}
	
	@Test
	public void testBigTree() {
		RedBlackTreeExercise<Integer> rbt = new RedBlackTreeExercise<Integer>();
		int shift = 7;
		for (int idx=100000;idx>0;--idx)
			rbt.add(idx + shift);
		for (int idx=100000;idx>0;--idx)
			assertEquals(idx - 1, rbt.rank(idx + shift));
	}
	@Test
	public void testBigTree2() {
		// reversing the order of inserting items into the list
		RedBlackTreeExercise<Integer> rbt = new RedBlackTreeExercise<Integer>();
		int shift = 7;
		for (int idx=0;idx<100000;++idx)
			rbt.add(idx + shift);
		for (int idx=0;idx<100000;++idx)
			assertEquals(idx, rbt.rank(idx + shift));
	}
	
	@Test
	public void testNotInt() {
		RedBlackTreeExercise<Card> rbt = new RedBlackTreeExercise<Card>();
		for (int rank=1;rank<=13;rank++)
			for (int suit=1;suit<=4;suit++)
				rbt.add(new Card(rank,suit));
		
		Card expected = new Card(13, 4);
		Card result = rbt.get(51); // looking for the ace of spades
		assertEquals(0, expected.compareTo(result));
	}
	
	
}
