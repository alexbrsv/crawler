package com.visualmeta.crawler.net;

/**
 * Factory for {@link ConnectionClient} creation
 * 
 * @author Alex Borisov
 *
 */
public interface ConnectionClientFactory {

	public ConnectionClient create();
	
}
