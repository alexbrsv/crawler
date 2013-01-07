package com.visualmeta.crawler.net;

/**
 * Connection exception 
 * 
 * @author Alex Borisov
 *
 */
@SuppressWarnings("serial")
public class ConnectionException extends RuntimeException {

	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionException(String message) {
		super(message);
	}
	
}