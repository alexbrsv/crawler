package com.visualmeta.crawler.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.visualmeta.crawler.logging.Logger;
import com.visualmeta.crawler.logging.LoggerFactory;

/**
 * Simple {@link ConnectionClient} implementation for HTTP protocol.
 * <br>
 * For the sake of simplicity only 200 status code is treated as success.
 * 
 * @author Alex Borisov
 * 
 */
public class HttpConnectionClient implements ConnectionClient {

	private static Logger logger = LoggerFactory.getLogger(HttpConnectionClient.class);

	private final int connectTimeout;
	
	public HttpConnectionClient(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	@Override
	public InputStream connect(URL url) throws ConnectionException {

		URLConnection connection;
		try {
			logger.finest("Opening connection to %s", url);
			connection = url.openConnection();
		} catch (IOException e) {
			throw new ConnectionException(String.format(
					"Exception on opening a connection to %s", url), e);
		}
		if (!(connection instanceof HttpURLConnection)) {
			throw new ConnectionException(String.format(
					"Unsupported protocol %s", url));
		}

		HttpURLConnection httpConn = (HttpURLConnection) connection;

		httpConn.setConnectTimeout(connectTimeout);
		httpConn.setInstanceFollowRedirects(true);

		// send the request
		try {
			httpConn.connect();
			logger.finest("Connected to %s", url);
		} catch (IOException e) {
			throw new ConnectionException(String.format(
					"Exception on connect to %s", url), e);
		}

		try {
			int responseCode = httpConn.getResponseCode();
			logger.finest("Response code for %s: %s", url, responseCode);
			if (responseCode != HttpURLConnection.HTTP_OK) {
				String error = readConnectionError(httpConn);
				if (error == null) {
					error = "HTTP response code " + responseCode;
				}
				throw new ConnectionException(String.format(
					"Failed to connect to %s: %s", url, error));				
			}

			return httpConn.getInputStream();
		} catch (IOException e) {
			throw new ConnectionException(String.format(
					"Exception on connect to %s", url), e);
		}
	}

	/**
	 * Reads a connection error stream and returns it as a string message.
	 * 
	 */
	private String readConnectionError(HttpURLConnection httpConn)
			throws IOException {

		InputStream errorStream = httpConn.getErrorStream();
		if (errorStream == null) {
			return null;
		}

		String charsetName = httpConn.getContentEncoding();
		Reader inputStreamReader;
		if (charsetName != null) {
			inputStreamReader = new InputStreamReader(errorStream, charsetName);
		} else {
			inputStreamReader = new InputStreamReader(errorStream);
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);

		String line = reader.readLine();
		StringBuilder result = new StringBuilder();
		while (line != null) {
			result.append(line);
			line = reader.readLine();
		}
		reader.close();
		
		return result.toString();
	}

}
