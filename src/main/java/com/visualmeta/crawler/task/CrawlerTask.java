package com.visualmeta.crawler.task;

import java.io.InputStream;
import java.net.URL;

import com.visualmeta.crawler.logging.Logger;
import com.visualmeta.crawler.logging.LoggerFactory;
import com.visualmeta.crawler.net.ConnectionClient;
import com.visualmeta.crawler.net.ConnectionException;

/**
 * Crawler task.
 * <p>
 * Makes a request, analyzes a response and delegates page processing to a
 * 
 * @author Alex Borisov
 * 
 */
public class CrawlerTask implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(CrawlerTask.class);

	private final URL url;
	private final ConnectionClient connection;
	private final PageHandlerFactory pageHandlerFactory;

	public CrawlerTask(URL url, ConnectionClient connection,
			PageHandlerFactory pageHandlerFactory) {

		this.url = url;
		this.connection = connection;
		this.pageHandlerFactory = pageHandlerFactory;
	}

	@Override
	public void run() {
		logger.info("Started crawling %s%n", url);

		InputStream inputStream;
		try {
			inputStream = connection.connect(url);

			if (inputStream != null) {
				PageHandler pageHandler = 
					pageHandlerFactory.createPageHandler();
				pageHandler.handle(url, inputStream);
			}
		} catch (ConnectionException ex) {
			logger.severe("Exception on crawling %s: %s", url, ex);
		}
		logger.info("Finished crawling %s%n", url);
	}

}