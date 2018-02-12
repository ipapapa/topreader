package com.yannis.in;

import java.util.Comparator;

/**
 * A comparator between two entries. The limitation of Java comparator is that
 * it requires integers. There are a few ways to handle longs but integers seems
 * to be enough for this challenge.
 * 
 * @author ipapapa
 *
 */
public class IdValueComparator implements Comparator<Ids> {

	@Override
	public int compare(Ids a, Ids b) {
		return b.getInt() - a.getInt();
	}

}
