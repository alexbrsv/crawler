package com.visualmeta.crawler;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.concurrent.BlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.visualmeta.crawler.net.ConnectionClient;
import com.visualmeta.crawler.net.ConnectionClientFactory;
import com.visualmeta.crawler.task.CrawlerTaskFactory;

/**
 * Tests {@link Crawler} class
 * 
 * @author Alex Borisov
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class CrawlerTest {

	@Mock
	private CrawlerTaskFactory taskFactory;
	@Mock
	private ConnectionClientFactory connectionFactory;
	@Mock
	private BlockingQueue<URL> linkQueue;
	@Mock
	private ConnectionClient connection;
	
	private Crawler crawler;

	@Before
	public void init() {
		crawler = new Crawler(taskFactory, connectionFactory, linkQueue);
	}

	@Test
	public void start() throws Exception {

		String startUrl = "http://example.com";
		String secondUrl = "http://google.com";
				
		Runnable firstTask = mock(Runnable.class);
		Runnable secondTask = mock(Runnable.class);
		when(connectionFactory.create()).thenReturn(connection);
		when(taskFactory.createCrawlerTask(new URL(startUrl), connection)).
			thenReturn(firstTask);
		when(taskFactory.createCrawlerTask(new URL(secondUrl), connection)).
			thenReturn(secondTask);
		when(linkQueue.take()).thenReturn(new URL(secondUrl));
		
		CrawlerConfig config = new CrawlerConfig(startUrl, 1, 1); 
		crawler.start(config);
		
		verify(connectionFactory).create();
		verify(taskFactory).createCrawlerTask(new URL(startUrl), connection);
		verify(taskFactory).createCrawlerTask(new URL(secondUrl), connection);
		verify(firstTask).run();
		verify(secondTask).run();
		verify(linkQueue).take();
		
		verifyNoMoreInteractions(taskFactory, connectionFactory, linkQueue, connection);
	}
	
	@Test(expected=RuntimeException.class)
	public void cannotCreateTask() throws Exception {
		when(taskFactory.createCrawlerTask(any(URL.class), any(ConnectionClient.class))).
			thenThrow(new RuntimeException("Something bad happened."));
		
		CrawlerConfig config = new CrawlerConfig("http://google.com", 1, 1); 
		crawler.start(config);
	}	

}