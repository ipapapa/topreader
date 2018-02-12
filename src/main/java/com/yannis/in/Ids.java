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
	int value;
	String address;
	final static Logger logger = LogManager.getLogger(Ids.class);

	// Constructor
	public Ids(String address, int value) {
		this.value = value;
		this.address = address;
	}

	// Used to print entry details
	public String toString() {
		return this.value + " " + " " + this.address;
	}

	public int getInt() {		
		return value;
	}
}