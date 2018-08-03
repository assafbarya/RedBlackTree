package com.vogella.junit.first;

public class Card implements Comparable<Card>{
	
	int rank; // 1 to 13
	int suit; // 1 to 4
	
	public Card( int rank, int suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	@Override
	public int compareTo(final Card rhs) {
		if (this.rank > rhs.rank) return 1;
		if (this.rank < rhs.rank) return -1;
		if (this.suit > rhs.suit) return 1;
		if (this.suit < rhs.suit) return -1;
		return 0;
	}
}

