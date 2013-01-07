package com.visualmeta.crawler.task;

import static org.mockito.Mockito.mock;

import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import junit.framework.Assert;

import org.junit.Test;

import com.visualmeta.crawler.net.ConnectionClient;

/**
 * Tests {@link CrawlerTaskFactoryImpl} class
 * 
 * @author Alex Borisov
 *
 */
public class CrawlerTaskFactoryImplTest {
	
	private BlockingQueue<URL> linkQueue = new LinkedBlockingQueue<URL>();
	private CrawlerTaskFactoryImpl factory = new CrawlerTaskFactoryImpl(linkQueue);
		
	@Test
	public void createCrawlerTask() throws Exception {
		ConnectionClient connection = mock(ConnectionClient.class);
		
		Runnable task = factory.createCrawlerTask(new URL("http://example.com"), connection);
		Assert.assertNotNull(task);
		Assert.assertEquals(CrawlerTask.class, task.getClass());
	}
	
	@Test
	public void createPageHandler() {
		PageHandler handler = factory.createPageHandler();
		Assert.assertNotNull(handler);
		Assert.assertEquals(ExtractLinksHandler.class, handler.getClass());
	}
	
}
