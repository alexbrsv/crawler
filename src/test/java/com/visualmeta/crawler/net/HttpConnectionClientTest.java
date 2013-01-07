package com.visualmeta.crawler.net;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Tests {@link HttpConnectionClient} class
 * 
 * @author Alex Borisov
 * 
 */
 @RunWith(PowerMockRunner.class)
 @PrepareForTest({URL.class, HttpConnectionClient.class, HttpURLConnection.class})
public class HttpConnectionClientTest {

	private ConnectionClient client;
	private URL url;

	@Before
	public void init() {
		client = new HttpConnectionClient(10000);
		url = mock(URL.class);
	}

	@Test(expected=ConnectionException.class)
	public void openConnectionException() throws IOException {
				
		when(url.openConnection()).thenThrow(
			new IOException("Exception on opepning a connection"));
		
		client.connect(url);
	}

	@Test
	public void connect() throws IOException {

		HttpURLConnection connection = mock(HttpURLConnection.class);
		when(url.openConnection()).thenReturn(connection);
		
		InputStream expected = mock(InputStream.class);
		when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
		when(connection.getInputStream()).thenReturn(expected);
		
		InputStream result = client.connect(url);
		
		Mockito.verify(connection).connect();
		Assert.assertEquals(expected, result);
	}

	@Test(expected=ConnectionException.class)
	public void openUnsupportedConnection() throws IOException {
				
		JarURLConnection connection = mock(JarURLConnection.class);
		when(url.openConnection()).thenReturn(connection);
		
		client.connect(url);
	}
	
	@Test(expected=ConnectionException.class)
	public void serverError() throws IOException {
				
		HttpURLConnection connection = mock(HttpURLConnection.class);
		when(url.openConnection()).thenReturn(connection);
		
		when(connection.getResponseCode()).
			thenReturn(HttpURLConnection.HTTP_INTERNAL_ERROR);
		
		client.connect(url);
	}	

}
