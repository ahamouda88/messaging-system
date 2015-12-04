package com.messaging.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.messaging.factory.QMessageFactory;
import com.messaging.model.QMessage;
import com.messaging.publisher.MessagePublisher;
import com.messaging.subscriber.ISubscriber;
import com.messaging.subscriber.MessageDisplaySubscriber;

public class MessagePublisherTest {
	
	/*
	 * Global Test Variables.
	 */
	private MessagePublisher<QMessage> publisher;
	private ISubscriber<QMessage> subscriberOne;
	private ISubscriber<QMessage> subscriberTwo;
	
	@Before
	public void testAddSubscriber(){
		publisher = new MessagePublisher<QMessage>();
		
		long expectedNo = 1;
		subscriberOne = new MessageDisplaySubscriber<QMessage>();
		publisher.addSubscriber(subscriberOne);		
		Assert.assertEquals(expectedNo, publisher.getNoOfSubscribers());
		
		publisher.addSubscriber(subscriberOne);
		Assert.assertEquals(expectedNo, publisher.getNoOfSubscribers());
		
		expectedNo = 2;
		subscriberTwo = new MessageDisplaySubscriber<QMessage>();
		publisher.addSubscriber(subscriberTwo);
		Assert.assertEquals(expectedNo, publisher.getNoOfSubscribers());
	}

	@Test
	public void testRemoveSubscriber(){
		long expectedNo = 1;
		publisher.removeSubscriber(subscriberOne);
		Assert.assertEquals(expectedNo, publisher.getNoOfSubscribers());
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullException(){
		publisher.addSubscriber(null);
	}
	
	@After
	public void testChange(){
		QMessage msg = QMessageFactory.createQMessage("Hello!");
		publisher.setChange(msg);		
	}
}
