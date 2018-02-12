package com.yannis.in;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;

import org.junit.Test;

import com.google.common.collect.MinMaxPriorityQueue;

public class inputTest {

	/**
	 * Tests if the file exists.
	 */
	@Test
	public void testFileExistance() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("testFile.txt").getFile());
		assertTrue(file.exists());
	}

	/**
	 * Tests if the file can be processed
	 */
	@Test
	public void testFile() {
		int topLargestValues = 10;
		Comparator<Ids> idCompare = new IdValueComparator();
		MinMaxPriorityQueue<Ids> pQueue = MinMaxPriorityQueue.orderedBy(idCompare).maximumSize(topLargestValues)
				.create();
		// FileReader reads text files in the default encoding.
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("src/main/resources/testFile.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fileReader);
		assertTrue(Application.writer(br, pQueue));
	}

}
