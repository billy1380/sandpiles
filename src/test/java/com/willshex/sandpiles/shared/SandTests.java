//
//  SandTests.java
//  sandpiles
//
//  Created by William Shakour (billy1380) on 19 Apr 2017.
//  Copyright © 2017 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.shared;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author William Shakour (billy1380)
 *
 */
public class SandTests {

	@Test
	public void testEqual () {
		Sand s = Sand.builder().shape(Tileable.Square)
				.grains(0, 1, 2, 3, 4, 5, 6, 7, 8).itemsPerRow(3).build();
		Sand t = Sand.builder().shape(Tileable.Square)
				.grains(0, 1, 2, 3, 4, 5, 6, 7, 8).itemsPerRow(3).build();

		assertEquals(s, s);
		assertEquals(t, t);
		assertEquals(s, t);
	}

	@Test
	public void testNotEqual () {
		Sand s = Sand.builder().shape(Tileable.Square)
				.grains(0, 1, 3, 3, 4, 5, 6, 7, 8).itemsPerRow(3).build();
		Sand t = Sand.builder().shape(Tileable.Square)
				.grains(0, 1, 2, 3, 4, 5, 6, 7, 8).itemsPerRow(3).build();
		Sand u = Sand.builder().shape(Tileable.Square)
				.grains(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).itemsPerRow(3).build();

		assertNotEquals(s, t);
		assertNotEquals(s, u);
		assertNotEquals(t, u);
	}

}
