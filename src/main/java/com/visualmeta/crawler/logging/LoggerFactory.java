package com.visualmeta.crawler.logging;

/**
 * Factory for producing Loggers
 * 
 * @author Alex Borisov
 *
 */
public final class LoggerFactory {

	public static Logger getLogger(String name) {
		return new LoggerImpl(name);
	}

	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}	
	
}