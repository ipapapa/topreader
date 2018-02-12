package com.yannis.in;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import com.google.common.collect.MinMaxPriorityQueue;

public class overallTest {

	String id1 = "1426828011 9";
	String id2 = "1426828028 350";
	String id3 = "1426828037 25";
	String id4 = "1426828056 231";
	String id5 = "1426828058 109";
	String id6 = "1426828066 111";

	/**
	 * Testing the an id
	 */
	@Test
	public void idValue() {
		String url = "1426828011";
		int goodNumber = 350;
		Ids id = new Ids(url, goodNumber);
		assertEquals(350, id.getInt());
	}

	/**
	 * Testing the size of the queue and peeking the largest value.
	 */
	@Test
	public void testInsertAndOrder() {

		MinMaxPriorityQueue<Ids> pQueue = returnQueue();

		Application.handleData(id1, pQueue);
		Application.handleData(id2, pQueue);
		Application.handleData(id3, pQueue);
		Application.handleData(id4, pQueue);
		Application.handleData(id5, pQueue);
		Application.handleData(id6, pQueue);

		assertEquals(6, pQueue.size());

		Ids idTop3 = new Ids("1426828028", 350);
		assertEquals(idTop3.getInt(), pQueue.peek().getInt());
		assertEquals(idTop3.toString(), pQueue.peek().toString());
	}

	/**
	 * Testing handling all the data from queue.
	 */
	@Test
	public void testReadQueue() {

		MinMaxPriorityQueue<Ids> pQueue = returnQueue();

		Application.handleData(id4, pQueue);
		assertTrue(Application.reader(pQueue, 1));
	}

	/**
	 * Testing partially process the queue.
	 */
	@Test
	public void testIncompleteQueue() {
		int X = 1;

		MinMaxPriorityQueue<Ids> pQueue = returnQueue();

		assertTrue(Application.handleData(id4, pQueue));
		assertTrue(Application.handleData(id5, pQueue));

		assertFalse(Application.reader(pQueue, X));

	}

	/**
	 * Testing same numeric values. We will poll twice making sure we match the
	 * requirements of the challenge.
	 * 
	 * @return
	 */
	@Test
	public void testSameNumericValues() {
		String id7 = "1426828066 200";
		String id8 = "1426828061 200";
		String id9 = "1426828062 115";
		String id10 = "1426828063 110";

		MinMaxPriorityQueue<Ids> pQueue = returnQueue();

		Application.handleData(id7, pQueue);
		Application.handleData(id8, pQueue);
		Application.handleData(id9, pQueue);
		Application.handleData(id10, pQueue);

		assertEquals("1426828066", pQueue.poll().address);
		assertEquals("1426828061", pQueue.poll().address);

	}

	private MinMaxPriorityQueue<Ids> returnQueue() {
		int topLargestValues = 10;

		Comparator<Ids> idCompare = new IdValueComparator();
		return MinMaxPriorityQueue.orderedBy(idCompare).maximumSize(topLargestValues).create();
	}

}
