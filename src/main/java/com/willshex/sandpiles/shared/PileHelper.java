//
//  PileHelper.java
//  sandpiles
//
//  Created by William Shakour (billy1380) on 19 Apr 2017.
//  Copyright © 2017 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.shared;

/**
 * @author William Shakour (billy1380)
 *
 */
public class PileHelper {
	/**
	 * This assumes that the grid is inset on odd rows
	* @param nth
	 * @param at
	 * @param x
	 * @return
	 */
	public static Integer neighbourHexagon (int nth, int at, int x) {
		int row = at / x;
		boolean evenRow = row % 2 == 0;

		int neighbour = -1;

		switch (nth) {
		case 0:
			neighbour = at - 1;
			break;
		case 1:
			neighbour = at - x;
			if (!evenRow) {
				neighbour--;
			}
			break;
		case 2:
			neighbour = at - x;
			if (evenRow) {
				neighbour++;
			}
			break;
		case 3:
			neighbour = at + 1;
			break;
		case 4:
			neighbour = at + x;
			if (evenRow) {
				neighbour++;
			}
			break;
		case 5:
			neighbour = at + x;
			if (!evenRow) {
				neighbour--;
			}
			break;
		}

		if (neighbour < 0) {
			neighbour = -1;
		} else {
			if ((nth == 0 || nth == 3) && neighbour / x != row) {
				neighbour = -1;
			}
		}

		return Integer.valueOf(neighbour);
	}

	/**
	 * @param nth
	 * @param at
	 * @return
	 */
	public static Integer neighbourSquare (int nth, int at, int x) {
		int row = at / x;

		int neighbour = -1;

		switch (nth) {
		case 0:
			neighbour = at - 1;
			break;
		case 1:
			neighbour = at - x;
			break;
		case 2:
			neighbour = at + 1;
			break;
		case 3:
			neighbour = at + x;
			break;
		}

		if (neighbour < 0) {
			neighbour = -1;
		} else {
			if ((nth == 0 || nth == 2) && neighbour / x != row) {
				neighbour = -1;
			}
		}

		return Integer.valueOf(neighbour);
	}

	/**
	 * This assumes that the grid is inset on odd rows
	 * @param nth
	 * @param at
	 * @return
	 */
	public static Integer neighbourTriangle (int nth, int at, int x) {
		int row = at / x;
		boolean evenIndex = at % 2 == 0, evenRow = row % 2 == 0,
				evenGrid = x % 2 == 0;
		boolean upsideDown = evenIndex == evenRow;

		if (!evenGrid && row > 0) {
			upsideDown = !upsideDown;
		}

		int neighbour = -1;

		switch (nth) {
		case 0:
			neighbour = at - 1;
			break;
		case 1:
			if (upsideDown) {
				neighbour = at + 1;
			} else {
				neighbour = at - x;
			}
			break;
		case 2:
			if (upsideDown) {
				neighbour = at + x;
			} else {
				neighbour = at + 1;
			}
			break;
		}

		if (neighbour < 0) {
			neighbour = -1;
		} else {
			int offRow = 1;

			if (upsideDown) {
				offRow = 2;
			}

			if (nth != offRow && neighbour / x != row) {
				neighbour = -1;
			}
		}

		return Integer.valueOf(neighbour);
	}
}
