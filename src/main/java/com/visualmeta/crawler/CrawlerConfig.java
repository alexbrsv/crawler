package com.visualmeta.crawler;

/**
 * Crawler configuration class.
 * 
 * @author Alex Borisov
 *
 */
class CrawlerConfig {

	private final String url;
	private final int threadsNumber;
	private final int urlsLimit;
	
	CrawlerConfig(String url, int threadsNumber, int urlsLimit) {
		super();
		this.url = url;
		this.threadsNumber = threadsNumber;
		this.urlsLimit = urlsLimit;
	}

	/** 
	 * @return Start URL crawler should start with.
	 */
	String getUrl() {
		return url;
	}

	/** 
	 * @return Number of threads.
	 */
	int getThreadsNumber() {
		return threadsNumber;
	}

	/** 
	 * @return Limit when crawler should stop.
	 */
	int getUrlsLimit() {
		return urlsLimit;
	}
	
}