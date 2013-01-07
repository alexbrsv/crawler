package com.visualmeta.crawler.task;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Tests {@link ExtractLinksHandler} class
 * 
 * @author Alex Borisov
 *
 */
public class ExtractLinksHandlerTest {

	@Test
	public void extractLinksFromExample() throws Exception {
		
		Queue<URL> queue = new LinkedList<URL>();		
		ExtractLinksHandler handler = new ExtractLinksHandler(queue);
		
		InputStream input = getClass().getResourceAsStream("/example.html");
		handler.handle(new URL("http://example.com"), input);
		
		Assert.assertEquals(1, queue.size());
		URL link = queue.element();
		Assert.assertEquals(new URL("http://www.iana.org/domains/special"), link);
	}
	
	@Test
	public void extractLinksBrokenLink() throws Exception {
		
		Queue<URL> queue = new LinkedList<URL>();		
		ExtractLinksHandler handler = new ExtractLinksHandler(queue);
		
		InputStream input = getClass().getResourceAsStream("/brokenlink.html");
		handler.handle(new URL("http://brokenlink.com"), input);
		
		Assert.assertEquals(0, queue.size());
	}

	@Test
	public void extractBadInput() throws Exception {
		
		Queue<URL> queue = new LinkedList<URL>();		
		ExtractLinksHandler handler = new ExtractLinksHandler(queue);
		
		InputStream input = mock(InputStream.class);
		when(input.read()).thenThrow(new IOException("Something went wrong."));
		handler.handle(new URL("http://ioexception.io"), input);
		
		Assert.assertEquals(0, queue.size());
	}	
}
