package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest
{
	private static final String[] TEST_EMAILS = {"ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
	private static final String[] TEST_HOST_NAME = {"blahblahblah.test.com", "...", "YoMamma289"};
	private static final int[] TEST_TIMEOUTS = {1000, 9999999, -1000};
	
	
	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception
	{
		email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception
	{
		
	}
	

	@Test
	public void testAddBcc() throws Exception
	{		
		// Add multiple email addresses to the BCC list
		email.addBcc(TEST_EMAILS);
		
		// Verify that all 3 emails were successfully added to the list
		assertEquals(3, email.getBccAddresses().size());
	}


	@Test
	public void testAddCc() throws Exception
	{		
		// Add multiple email addresses to the CC list
		email.addCc(TEST_EMAILS);
		
		// Verify that all 3 emails were successfully added to the list
		assertEquals(3, email.getCcAddresses().size());
	}


	@Test
	public void testAddReplyTo() throws Exception
	{		
		// addReplyTo only accepts one email at a time so I have to loop through the array
		for (String emailStr : TEST_EMAILS)
		{
			email.addReplyTo(emailStr, null); // name is null
		}
		
		// Verify that all 3 emails were added
		assertEquals(3, email.getReplyToAddresses().size());
	}


	@Test
	public void testGetHostName()
	{
		// Loop through multiple host names and test each one
		for (String host : TEST_HOST_NAME)
		{
			// Set the host name
			email.setHostName(host);
			
			// Verify that getHostName returns the same value
			assertEquals(host, email.getHostName());
		}
	}

	@Test
	public void testGetHostNameNull()
	{
		// No host name is set so the default should be null
		assertEquals(null, email.getHostName());
	}


	@Test
	public void testGetMailSession() throws Exception
	{
		// Loop through host names
		for (String host : TEST_HOST_NAME)
		{
			email = new EmailConcrete(); // reset so session is not reused
			email.setHostName(host);
			
			// Verify that the session was created and has the correct host property
			assertEquals(host, email.getMailSession().getProperty("mail.smtp.host"));
		}
	}

	@Test
	public void testGetMailSessionNoHost()
	{
		try
		{
			// No host is set so this should throw an EmailException
			email.getMailSession();
		}
		catch (EmailException e)
		{
			// Verify the exception message matches expected behavior
			assertEquals("Cannot find valid hostname for mail session ", e.getMessage());
		}
	}


	@Test
	public void testGetSentDate()
	{
		// If no date is set the method should return the current date
		assertEquals(true, email.getSentDate() != null);
	}

	@Test
	public void testGetSentDateSet()
	{
		// Create a specific date
		java.util.Date testDate = new java.util.Date();
		
		// Set the sent date
		email.setSentDate(testDate);
		
		// Verify that the same date is returned
		assertEquals(testDate, email.getSentDate());
	}


	@Test
	public void testGetSocketConnectionTimeout()
	{
		// Verify default timeout is a non-negative value
		assertEquals(true, email.getSocketConnectionTimeout() >= 0);
	}

	@Test
	public void testGetSocketConnectionTimeoutSet()
	{
		// Test multiple timeout values
		for (int timeout : TEST_TIMEOUTS)
		{
			// Set timeout
			email.setSocketConnectionTimeout(timeout);
			
			// Verify the value was stored correctly
			assertEquals(timeout, email.getSocketConnectionTimeout());
		}
	}


	@Test
	public void testSetFrom() throws Exception
	{
		// Loop through valid email addresses
		for (String emailStr : TEST_EMAILS)
		{
			// Set the "from" address
			email.setFrom(emailStr);
			
			// Verify the address was correctly stored
			assertEquals(emailStr, email.getFromAddress().getAddress());
		}
	}