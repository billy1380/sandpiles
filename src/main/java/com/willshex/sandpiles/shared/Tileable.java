//
//  Tileable.java
//  sandpiles
//
//  Created by William Shakour (billy1380) on 18 Apr 2017.
//  Copyright © 2017 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.shared;

/**
 * @author William Shakour (billy1380)
 *
 */
public enum Tileable {
	Triangle(3),
	Square(4),
	Hexagon(6);

	private int sides;

	public int getSides () {
		return sides;
	}

	Tileable (int sides) {
		this.sides = sides;
	}

}
