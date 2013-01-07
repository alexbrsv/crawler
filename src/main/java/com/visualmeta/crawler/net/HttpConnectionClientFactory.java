package com.visualmeta.crawler.net;

/**
 * {@link ConnectionClientFactory} implementation, creates
 * {@link HttpConnectionClient} instances
 * 
 * @author Alex Borisov
 * 
 */
public class HttpConnectionClientFactory implements ConnectionClientFactory {

	private static final int DEFAULT_CONNECTION_TIMEOUT = 10000;

	@Override
	public HttpConnectionClient create() {
		return new HttpConnectionClient(DEFAULT_CONNECTION_TIMEOUT);
	}

}
