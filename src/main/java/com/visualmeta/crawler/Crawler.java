package com.visualmeta.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.visualmeta.crawler.logging.Logger;
import com.visualmeta.crawler.logging.LoggerFactory;
import com.visualmeta.crawler.net.ConnectionClient;
import com.visualmeta.crawler.net.ConnectionClientFactory;
import com.visualmeta.crawler.net.HttpConnectionClientFactory;
import com.visualmeta.crawler.task.CrawlerTaskFactory;
import com.visualmeta.crawler.task.CrawlerTaskFactoryImpl;

/**
 * Crawler start class
 * 
 * @author Alex Borisov
 */
public class Crawler {

	private static Logger logger = LoggerFactory.getLogger(Crawler.class);
	
	private final CrawlerTaskFactory taskFactory;
	private final ConnectionClientFactory connectionFactory;
	private final BlockingQueue<URL> linkQueue;
	
	public Crawler(CrawlerTaskFactory taskFactory,
			ConnectionClientFactory connectionFactory,
			BlockingQueue<URL> linkQueue) {
		this.taskFactory = taskFactory;
		this.connectionFactory = connectionFactory;
		this.linkQueue = linkQueue;
	}

	void start(CrawlerConfig crawlerConfig) throws InterruptedException, MalformedURLException {
		
		int threadsNumber = crawlerConfig.getThreadsNumber();
		int limit = crawlerConfig.getUrlsLimit();
		String startUrl = crawlerConfig.getUrl();
		
		ExecutorService executorService = 
			Executors.newFixedThreadPool(threadsNumber);
		
		try {
			ConnectionClient connection = connectionFactory.create();
			Runnable startTask = taskFactory.createCrawlerTask(new URL(startUrl), connection);
			executorService.execute(startTask);

			for (int i = 0; i < limit; i++) {
				URL url = linkQueue.take();
				Runnable crawlerTask = 
					taskFactory.createCrawlerTask(url, connection);
				logger.finest("URL has been read from a queue: %s", url);
				executorService.execute(crawlerTask);
			}
		} finally {
			executorService.shutdown();
		}
	}

	public static void main(String... args) throws Exception {
		
		ConsoleConfigParser configParser = new ConsoleConfigParser();
		CrawlerConfig config = configParser.parse(args);
		if (config == null) {
			// config could not be parsed
			return;
		}
		
		BlockingQueue<URL> linkQueue = new LinkedBlockingQueue<URL>();
		CrawlerTaskFactory taskFactory = new CrawlerTaskFactoryImpl(linkQueue);
		ConnectionClientFactory connectionFactory = new HttpConnectionClientFactory();
		
		Crawler crawler = new Crawler(taskFactory, connectionFactory, linkQueue);
		
		crawler.start(config);
	}
}
