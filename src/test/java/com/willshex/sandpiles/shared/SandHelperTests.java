//
//  SandHelperTests.java
//  sandpiles
//
//  Created by William Shakour (billy1380) on 19 Apr 2017.
//  Copyright © 2017 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.shared;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author William Shakour (billy1380)
 *
 */
public class SandHelperTests {

	/**
	 * Test method for {@link com.willshex.sandpiles.PileHelper#neighbourHexagon(int, int, int)}.
	 */
	@Test
	public void testNeighbourHexagon () {
		test(new int[] { 3, 0, 1, 5, 7, 6 }, H, 4, 3);
		test(new int[] { 6, 4, 5, 8, 11, 10 }, H, 7, 3);
	}

	/**
	 * Test method for {@link com.willshex.sandpiles.PileHelper#neighbourSquare(int, int, int)}.
	 */
	@Test
	public void testNeighbourSquare () {
		test(new int[] { 3, 1, 5, 7 }, S, 4, 3);
		test(new int[] { 4, 2, -1, 8 }, S, 5, 3);

		test(new int[] { 5, 2, 7, 10 }, S, 6, 4);
		test(new int[] { 8, 5, 10, 13 }, S, 9, 4);
	}

	/**
	 * Test method for {@link com.willshex.sandpiles.PileHelper#neighbourTriangle(int, int, int)}.
	 */
	@Test
	public void testNeighbourTriangle () {
		test(new int[] { 0, -1, 2 }, T, 1, 3);
		test(new int[] { 3, 5, 7 }, T, 4, 3);

		test(new int[] { 0, -1, 2 }, T, 1, 4);
		test(new int[] { 4, 6, 9 }, T, 5, 4);
		test(new int[] { 5, 2, 7 }, T, 6, 4);
	}

	private interface Finder {
		int find (int i, int at, int width);

		int n ();
	}

	private static final Finder T = new Finder() {
		@Override
		public int find (int i, int at, int width) {
			return PileHelper.neighbourTriangle(i, at, width).intValue();
		}

		@Override
		public int n () {
			return 3;
		}
	};

	private static final Finder S = new Finder() {
		@Override
		public int find (int i, int at, int width) {
			return PileHelper.neighbourSquare(i, at, width).intValue();
		}

		@Override
		public int n () {
			return 4;
		}
	};

	private static final Finder H = new Finder() {
		@Override
		public int find (int i, int at, int width) {
			return PileHelper.neighbourHexagon(i, at, width).intValue();
		}

		@Override
		public int n () {
			return 6;
		}
	};

	/**
	 * @param neightbors
	 * @param i
	 * @param j
	 */
	private void test (int[] expected, Finder finder, int at, int width) {
		assertEquals(expected.length, finder.n());

		for (int i = 0; i < finder.n(); i++) {
			assertEquals(expected[i], finder.find(i, at, width));
		}
	}

}
