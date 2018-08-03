package com.vogella.junit.first;

public class MyClass {
	
	private class MyPrivateClass  {
		private int x;
	}
	
  public int multiply(int x, int y) {
    // the following is just an example
	MyPrivateClass c = new MyPrivateClass();
	c.x = 10;
	System.out.println(c.x);
    if (x > 999) {
      throw new IllegalArgumentException("X should be less than 1000");
    }
    return x * y;
  }
}