package com.visualmeta.crawler.net;

import java.io.InputStream;
import java.net.URL;

/**
 * Simple connection facade allowing getting content input stream by URL.
 * 
 * @author Alex Borisov
 * 
 */
public interface ConnectionClient {

	/**
	 * Connects to a given <tt>url</tt> and returns its content input stream.
	 * @throws ConnectionException in case of any error 
	 */
	public InputStream connect(URL url) throws ConnectionException;
}
