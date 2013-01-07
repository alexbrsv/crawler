package com.visualmeta.crawler.task;

import java.net.URL;

import com.visualmeta.crawler.net.ConnectionClient;

/**
 * Factory for crawler task creation
 * 
 * @author Alex Borisov
 * 
 */
public interface CrawlerTaskFactory {

	/**
	 * @return Crawler task that should crawl a page identified by <tt>url</tt>
	 */
	public Runnable createCrawlerTask(URL url, ConnectionClient connection);
	
}
