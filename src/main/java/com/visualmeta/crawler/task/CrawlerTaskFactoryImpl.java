package com.visualmeta.crawler.task;

import java.net.URL;
import java.util.concurrent.BlockingQueue;

import com.visualmeta.crawler.net.ConnectionClient;

/**
 * Default {@link CrawlerTaskFactory} and {@link PageHandlerFactory} imnplementation.
 * Creates 
 * 
 * @author Alex Borisov
 *
 */
public class CrawlerTaskFactoryImpl implements CrawlerTaskFactory, PageHandlerFactory {

	private final BlockingQueue<URL> linkQueue;
	
	public CrawlerTaskFactoryImpl(BlockingQueue<URL> linkQueue) {
		this.linkQueue = linkQueue;
	}

	@Override
	public Runnable createCrawlerTask(URL url, ConnectionClient connection) {
		return new CrawlerTask(url, connection, this);
	}

	@Override
	public PageHandler createPageHandler() {
		return new ExtractLinksHandler(linkQueue);
	}

}
