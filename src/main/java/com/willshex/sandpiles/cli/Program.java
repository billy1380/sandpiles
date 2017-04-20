//
//  Program.java
//  sandpiles
//
//  Created by William Shakour (billy1380) on 18 Apr 2017.
//  Copyright © 2017 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.cli;

import com.willshex.sandpiles.shared.Sand;
import com.willshex.sandpiles.shared.Tileable;

/**
 * @author William Shakour (billy1380)
 *
 */
public class Program {
	public static void main (String[] args) {

		test1();
		test2();
		test3();
		test4();
		test5();
		test6();
		test7();
		test8();

	}

	private static void test1 () {
		test("test1", Tileable.Square, new int[] { 1, 2, 0, 2, 1, 1, 0, 1, 3 },
				new int[] { 2, 1, 3, 1, 0, 1, 0, 1, 0 });
	}

	private static void test2 () {
		test("test2", Tileable.Square, new int[] { 2, 2, 0, 2, 1, 1, 0, 1, 3 },
				new int[] { 2, 1, 3, 1, 0, 1, 0, 1, 0 });
	}

	private static void test3 () {
		test("test3", Tileable.Square, new int[] { 2, 2, 0, 2, 1, 1, 0, 1, 3 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 });
	}

	private static void test4 () {
		test("test4", Tileable.Square, new int[] { 2, 2, 0, 2, 1, 1, 0, 1, 3 },
				new int[] { 2, 1, 2, 1, 0, 1, 2, 1, 2 });
	}

	private static void test5 () {
		test("test5", Tileable.Square, new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				new int[] { 3, 3, 3, 3, 1, 3, 3, 3, 3 });
	}

	private static void test6 () {
		test("test6", Tileable.Square, new int[] { 2, 2, 2, 2, 2, 2, 2, 2, 2 },
				new int[] { 2, 1, 2, 1, 0, 1, 2, 1, 2 });
	}

	private static void test7 () {
		test("test7", Tileable.Square, new int[] { 2, 1, 2, 1, 0, 1, 2, 1, 2 },
				new int[] { 2, 1, 2, 1, 0, 1, 2, 1, 2 });
	}

	private static void test8 () {
		test("test8", Tileable.Square, new int[] { 2, 1, 1, 2 },
				new int[] { 2, 2, 2, 2 });
	}

	private static void test (String name, Tileable type, int[] sand1,
			int[] sand2) {
		Sand g1 = Sand.builder().shape(Tileable.Square).grains(sand1).build();
		Sand g2 = Sand.builder().shape(Tileable.Square).grains(sand2).build();
		Sand gT = g2.sum(g1);

		while (gT.topple());
		System.out.println(name);
		System.out.println(gT);
	}
}
