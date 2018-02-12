package com.yannis.in;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Comparator;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import com.google.common.collect.MinMaxPriorityQueue;

public class mainTest extends Application {

	@After
	public void tearDown() throws Exception {
		instance = null;
	}

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Test(expected = Exception.class)
	public void testIncorrectInput() throws Exception {
		instance = new CrashAndBurn();
		String[] args = { "one", "2", "3" };
		exit.expectSystemExitWithStatus(1);
		main(args);
	}

	/**
	 * Tests stdin processing by the writer
	 * 
	 * @throws IOException
	 */
	@Test
	public void teststdin() throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream("31431431 23".getBytes());
		System.setIn(in);
		BufferedReader br = null;
		br = readStdin(br);
		Comparator<Ids> idCompare = new IdValueComparator();
		MinMaxPriorityQueue<Ids> pQueue = MinMaxPriorityQueue.orderedBy(idCompare).maximumSize(10).create();

		writer(br, pQueue);
		assertEquals(pQueue.poll().value, "23");
		System.setIn(System.in);
	}

	private static class CrashAndBurn extends Application {
		@Override
		protected void run(String string1) throws Exception {
			throw new Exception();
		}
	}

}
