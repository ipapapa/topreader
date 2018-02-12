package com.yannis.in;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.regex.PatternSyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.MinMaxPriorityQueue;

/**
 * This is the main class. The application either accepts one input argument for
 * X and uses the stdin or two input arguments for X and the filename.
 * 
 * We use the second value to cap a PriorityQueue with a customer provided
 * comparator. PriorityQueue provides O(log(n)) time for the enqueing and
 * dequeing methods (offer, poll, remove() and add); linear time for the
 * remove(Object) and contains(Object) methods; and constant time for the
 * retrieval methods (peek, element, and size).
 * 
 * More information can be found in the README.md
 * 
 * @author ipapapa
 *
 */
public class Application {
	final static Logger logger = LogManager.getLogger(Application.class);

	Application instance;

	protected Application() {
	}

	public static BufferedReader readStdin(BufferedReader br) {
		return new BufferedReader(new InputStreamReader(System.in));
	}

	protected static BufferedReader readFile(BufferedReader br, String filename) {
		FileReader fileReader = null;
		try {
			// FileReader reads text files in the default encoding.
			fileReader = new FileReader(filename);
			// Always wrap FileReader in BufferedReader.
			br = new BufferedReader(fileReader);

		} catch (FileNotFoundException ex) {
			logger.error("Unable to open file '" + filename + "'");
			ex.printStackTrace();
		}
		return new BufferedReader(fileReader);
	}

	/**
	 * Writes to the priority queue by reading from the given file
	 * 
	 * @param filename
	 * @param pQueue
	 * @return whether the writer has succeeded
	 */
	protected static boolean writer(BufferedReader br, MinMaxPriorityQueue<Ids> pQueue) {
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				if (handleData(line, pQueue) != true) {
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Reads from the Priority queue and prints out the elements. We return a
	 * boolean to indicate if all items have been read.
	 * 
	 * @param pQueue
	 * @return boolean
	 */
	protected static boolean reader(MinMaxPriorityQueue<Ids> pQueue, int topValues) {
		int count = 0;
		logger.info("---------");
		while (pQueue.peek() != null && count < topValues) {
			logger.info(pQueue.poll().address);
			count++;
		}
		logger.info("---------");

		if (pQueue.peek() != null) {
			return false;
		}

		return true;
	}

	/**
	 * Adding to the queue.
	 * 
	 * @param line
	 * @param pQueue
	 * @return handling the data properly
	 */
	public static boolean handleData(String line, MinMaxPriorityQueue<Ids> pQueue) {

		logger.debug(line);
		try {
			String[] output = line.split("\\s+");
			Ids id = new Ids(output[0], output[1]);
			pQueue.add(id);
		} catch (PatternSyntaxException e) {
			logger.error("There was some issue proccessing this line");
			return false;
		}
		return true;

	}

	/**
	 * This is where we start... Based on the number of input arguments we either
	 * read the stdin or the provided file.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int topValues = 0;
		BufferedReader br = null;

		if (args.length == 1) {
			logger.info("You have provided one argument --> expecting stdin");
			topValues = Integer.parseInt(args[0]); // exception will be thrown if it is not an integer
			logger.info("X= " + topValues);
			// BufferedReader from stdin
			br = readStdin(br);

		} else if (args.length == 2) {
			logger.info("You have provided two arguments --> Reading from a file");
			topValues = Integer.parseInt(args[0]);
			String filename = args[1];
			logger.info("Filename: " + filename);
			logger.info("X= " + topValues);
			br = readFile(br, filename);
		} else {
			logger.error("The number of provided arguments is not correct: " + args.length);
			System.exit(1);
		}

		// Create a comparator
		Comparator<Ids> movCompare = new IdValueComparator();
		/*
		 * Construct a Min Max priority queue based on Guava. We use topValues as the
		 * maximum capacity.
		 */

		MinMaxPriorityQueue<Ids> pQueue = MinMaxPriorityQueue.orderedBy(movCompare).maximumSize(topValues).create();

		if (writer(br, pQueue) == true) {
			reader(pQueue, topValues);
		} else {
			logger.error("There was some issue with reading the data");
			System.exit(1);
		}

		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.exit(0);
	}

	protected void run(String string) throws Exception {
	}

}
