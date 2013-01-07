package com.visualmeta.crawler.logging;

import static org.mockito.Mockito.*;

import java.util.logging.Level;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.visualmeta.crawler.logging.Logger;
import com.visualmeta.crawler.logging.LoggerImpl;

/**
 * Tests {@link LoggerImpl} class
 * 
 * @author Alex Borisov
 */
@RunWith(MockitoJUnitRunner.class)
public class LoggerImplTest {

	@Mock
	private java.util.logging.Logger delegatee;

	private Logger logger;

	@Before
	public void init() {
		this.logger = new LoggerImpl(delegatee);
	}

	@Test
	public void severe() {
		when(delegatee.isLoggable(Level.SEVERE)).thenReturn(Boolean.TRUE);
		when(delegatee.getName()).thenReturn(LoggerImplTest.class.getName());
		logger.severe("Severe message to log");

		verify(delegatee).logp(Level.SEVERE, LoggerImplTest.class.getName(),
				null, "Severe message to log");
	}

	@Test
	public void warning() {
		when(delegatee.isLoggable(Level.WARNING)).thenReturn(Boolean.TRUE);
		when(delegatee.getName()).thenReturn(LoggerImplTest.class.getName());
		logger.warning("%s message to log", "Warning");

		verify(delegatee).logp(Level.WARNING, LoggerImplTest.class.getName(),
				null, "Warning message to log");
	}
	
	@Test
	public void info() {
		when(delegatee.isLoggable(Level.INFO)).thenReturn(Boolean.TRUE);
		when(delegatee.getName()).thenReturn(LoggerImplTest.class.getName());
		logger.info("%s %s message to log", null, "info");

		verify(delegatee).logp(Level.INFO, LoggerImplTest.class.getName(),
				null, "null info message to log");
	}
	
	@Test
	public void config() {
		when(delegatee.isLoggable(Level.CONFIG)).thenReturn(Boolean.TRUE);
		when(delegatee.getName()).thenReturn(LoggerImplTest.class.getName());
		logger.config("%s message to log %s", "Config", "%");

		verify(delegatee).logp(Level.CONFIG, LoggerImplTest.class.getName(),
				null, "Config message to log %%");
	}
	
	@Test
	public void fine() {
		when(delegatee.isLoggable(Level.FINE)).thenReturn(Boolean.FALSE);
		
		logger.fine("Message to log");
		
		verify(delegatee).isLoggable(Level.FINE);
		verifyNoMoreInteractions(delegatee);
	}
	
	@Test
	public void finer() {
		when(delegatee.isLoggable(Level.FINER)).thenReturn(Boolean.TRUE);
		when(delegatee.getName()).thenReturn("com.whatever.WhateverClass");
		
		logger.finer("Fine message with %s", "argument");

		verify(delegatee).logp(Level.FINER, "com.whatever.WhateverClass",
				null, "Fine message with argument");
	}	
}
