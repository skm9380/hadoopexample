package com.skm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseLog {
	private static final Logger log = LoggerFactory.getLogger(ParseLog.class);
	public static String getHour(String timeStamp){
		timeStamp= timeStamp.substring(timeStamp.indexOf(':')+1,timeStamp.indexOf(':', timeStamp.indexOf(':')+1)) ;
		return timeStamp;
	}
	
	public static void main (String args[]){
		log.debug(getHour("01/Jul/1995:00:00:11 -0400"));
	}
}
