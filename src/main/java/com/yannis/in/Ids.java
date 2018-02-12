package com.yannis.in;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class to represent a unique ID. A unique ID is comprised of the address and
 * a numeric value.
 * 
 * @author ipapapa
 */
public class Ids {
	String value;
	String address;
	final static Logger logger = LogManager.getLogger(Ids.class);

	// Constructor
	public Ids(String address, String value) {
		this.value = value;
		this.address = address;
	}

	// Used to print entry details
	public String toString() {
		return this.value + " " + " " + this.address;
	}

	public int valueToInt() {
		int iValue = 0;
		try {
			// Parse integer takes into account a badly formatted integer
			iValue = Integer.parseInt(this.value);

			// this is a little a too aggressive
			if (iValue >= Integer.MAX_VALUE) {
				logger.error("Value is too big");
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			logger.error("Value : " + value + " cannot be converted to int");
			e.printStackTrace();
		}
		return iValue;
	}
}