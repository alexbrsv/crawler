package com.visualmeta.crawler.task;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.visualmeta.crawler.net.ConnectionClient;
import com.visualmeta.crawler.net.ConnectionException;

/**
 * Tests {@link CrawlerTask} class
 * 
 * @author Alex Borisov
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class CrawlerTaskTest {

	@Mock
	private ConnectionClient connection;
	@Mock
	private PageHandlerFactory pageHandlerFactory;

	@Test
	public void executeTask() throws Exception {
		URL url = new URL("http:\\example.com");
				
		InputStream inputStream = mock(InputStream.class);
		PageHandler pageHandler = mock(PageHandler.class);
		
		when(connection.connect(url)).thenReturn(inputStream);
		when(pageHandlerFactory.createPageHandler()).thenReturn(pageHandler);
		
		CrawlerTask task = new CrawlerTask(url, connection, pageHandlerFactory);
		task.run();
		
		verify(connection).connect(url);
		verify(pageHandlerFactory).createPageHandler();
		verify(pageHandler).handle(url, inputStream);
	}
	
	@Test
	public void executeTaskNoConnection() throws Exception {
		URL url = new URL("http:\\google.com");
				
		when(connection.connect(url)).thenReturn(null);
		
		CrawlerTask task = new CrawlerTask(url, connection, pageHandlerFactory);
		task.run();
		
		verify(connection).connect(url);
		verifyNoMoreInteractions(connection, pageHandlerFactory);
	}
	
	@Test
	public void executeTasConnectionException() throws Exception {
		URL url = new URL("http:\\google.com");
				
		when(connection.connect(url)).thenThrow(new ConnectionException("Error on connection"));
		
		CrawlerTask task = new CrawlerTask(url, connection, pageHandlerFactory);
		task.run();
		
		verify(connection).connect(url);
		verifyNoMoreInteractions(connection, pageHandlerFactory);
	}

}
