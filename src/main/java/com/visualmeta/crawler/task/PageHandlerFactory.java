package com.visualmeta.crawler.task;


/**
 * Factory for page handler creation
 * 
 * @author Alex Borisov
 *
 */
public interface PageHandlerFactory {
	
	/**
	 * @return {@link PageHandler} implementation that should provide a strategy
	 *         how to handle a web page.
	 */
	public PageHandler createPageHandler();
	
}
