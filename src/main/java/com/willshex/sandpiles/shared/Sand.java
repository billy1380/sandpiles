//
//  Sand.java
//  sandpiles
//
//  Created by William Shakour (billy1380) on 18 Apr 2017.
//  Copyright © 2017 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.shared;

import java.util.HashSet;

import com.willshex.sandpiles.shared.util.SparseArray;

/**
 * @author William Shakour (billy1380)
 *
 */
public class Sand {

	private Sand () {}

	private Tileable shape;
	private SparseArray<Pile> piles;
	private HashSet<Integer> exceeding;
	private HashSet<Integer> exceedingNext;
	private int itemsPerRow = -1;

	public boolean topple () {
		boolean toppled = false;

		if (exceeding != null && exceeding.size() > 0) {
			System.out.println("need to topple");
			//			System.out.println(this);

			Integer neighbourIndex;
			for (Integer at : exceeding) {
				System.out.println("processessing " + at);

				piles.get(at.intValue()).value -= shape.getSides();

				testAndAdd(at, exceedingNext);

				for (int i = 0; i < shape.getSides(); i++) {
					neighbourIndex = neighbour(i, at.intValue());

					if (neighbourIndex >= 0 && neighbourIndex < piles.size()) {
						piles.get(neighbourIndex).value += 1;

						testAndAdd(neighbourIndex, exceedingNext);
					}
				}

				//System.out.println(this);
			}

			swapExceedingLists();

			toppled = true;

			System.out.println("toppled");
			System.out.println(this);
		}

		return toppled;
	}

	/**
	 * 
	 */
	private void swapExceedingLists () {
		HashSet<Integer> temp = exceeding;
		temp.clear();

		exceeding = exceedingNext;
		exceedingNext = temp;
	}

	/**
	 * @param at
	 * @param list
	 */
	private void testAndAdd (Integer at, HashSet<Integer> list) {
		if (piles.get(at.intValue()).value > 3) {
			list.add(at);
		} else {
			if (list.contains(at)) {
				list.remove(at);
			}
		}
	}

	/**
	 * 
	 * @param nth
	 * @param at
	 * @return
	 */
	private Integer neighbour (int nth, int at) {
		Integer neighbour = Integer.valueOf(0);

		switch (shape) {
		case Triangle:
			neighbour = PileHelper.neighbourTriangle(nth, at, getItemsPerRow());
			break;
		case Square:
			neighbour = PileHelper.neighbourSquare(nth, at, getItemsPerRow());
			break;
		case Hexagon:
			neighbour = PileHelper.neighbourHexagon(nth, at, getItemsPerRow());
			break;
		}

		return neighbour;
	}

	public void add (int grains, int at) {
		if (piles == null) {
			piles = new SparseArray<>();
			exceeding = new HashSet<Integer>();
			exceedingNext = new HashSet<Integer>();
		}

		Pile s;
		if ((s = piles.get(at)) != null) {
			s.value += grains;
		} else {
			s = new Pile();
			s.generation = 0;
			s.value = grains;
			piles.put(at, s);
		}

		testAndAdd(Integer.valueOf(at), exceeding);
	}

	public static class Builder {
		private Sand g = new Sand();

		public Builder shape (Tileable shape) {
			g.setShape(shape);
			return this;
		}

		public Builder start (int grains, int at) {
			g.add(grains, at);
			return this;
		}

		public Builder grains (int... grains) {
			for (int i = 0; i < grains.length; i++) {
				start(grains[i], i);
			}
			return this;
		}

		public Builder itemsPerRow (int itemsPerRow) {
			g.itemsPerRow = itemsPerRow;
			return this;
		}

		public Sand build () {
			return g;
		}
	}

	public static Builder builder () {
		return new Builder();
	}

	public void setShape (Tileable shape) {
		this.shape = shape;
	}

	public int grains (int at) {
		return piles.get(at).value;
	}

	/**
	 * @param g1
	 * @return
	 */
	public Sand sum (Sand g1) {
		if (g1.shape != this.shape)
			throw new RuntimeException("Types are not compatible");

		Sand r = new Sand();
		r.shape = shape;

		if (piles != null) {
			for (int i = 0; i < piles.size(); i++) {
				r.add(piles.get(i).value + g1.piles.get(i).value, i);
			}
		}

		return r;
	}

	/**
	 * @return
	 */
	public int getItemsPerRow () {
		return itemsPerRow > 0 ? itemsPerRow
				: (itemsPerRow = (int) Math.sqrt(piles.size()));
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals (Object obj) {
		boolean equal = super.equals(obj);
		if (!equal && obj instanceof Sand) {
			equal = piles.equals(((Sand) obj).piles);

			if (!equal) {
				equal = piles.size() == ((Sand) obj).piles.size();

				if (equal) {
					for (int i = 0; i < piles.size(); i++) {
						Pile pv = piles.get(piles.keyAt(i));
						Pile objPv = ((Sand) obj).piles.get(piles.keyAt(i));

						if ((pv == null && objPv != null)
								|| (pv != null && objPv == null)
								|| (pv != null && objPv != null
										&& pv.value != objPv.value)) {
							equal = false;
							break;
						}
					}
				}
			}
		}

		return equal;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString () {
		StringBuffer b = new StringBuffer();
		int x = getItemsPerRow();
		for (int i = 0; i < piles.size(); i++) {
			if (i % x == 0 && b.length() > 0) {
				b.setLength(b.length() - 1);
				b.append("\n");
				for (int j = 0; j < (x * 2) - 1; j++) {
					b.append("-");
				}
				b.append("\n");
			}

			b.append(piles.get(i).value);
			b.append("|");
		}
		b.setLength(b.length() - 1);

		return b.toString();
	}

}
