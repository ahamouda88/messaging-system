package com.messaging.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.messaging.factory.QMessageFactory;
import com.messaging.model.QMessage;
import com.messaging.publisher.IPublisher;
import com.messaging.publisher.MessagePublisher;
import com.messaging.publisher.PublisherService;
import com.messaging.subscriber.ISubscriber;
import com.messaging.subscriber.MessageDisplaySubscriber;

public class PublisherServiceTest {

	private PublisherService<QMessage> publisherService;
	private ISubscriber<QMessage> subscriberOne;
	private ISubscriber<QMessage> subscriberTwo;	
	
	@Before
	public void init(){		
		IPublisher<QMessage> publisher = new MessagePublisher<QMessage>();
		publisherService = new PublisherService<QMessage>(publisher);
		
		long expectedId = 1;
		subscriberOne = new MessageDisplaySubscriber<QMessage>();
		long id1 = publisherService.addSubscriber(subscriberOne);
		Assert.assertEquals(expectedId, id1);
		
		expectedId = 2;
		subscriberTwo = new MessageDisplaySubscriber<QMessage>();
		long id2 = publisherService.addSubscriber(subscriberTwo);
		Assert.assertEquals(expectedId, id2);
		
		expectedId = -1;
		long id3 = publisherService.addSubscriber(subscriberTwo);
		Assert.assertEquals(expectedId, id3);
	}
	
	@Test
	public void testReSubscribeAndChange(){
		QMessage msg = QMessageFactory.createQMessage("Hello!");
		publisherService.setChange(msg);
		
		long removedSubscriberId = 2;
		publisherService.removeSubscriber(subscriberTwo);
		
		QMessage msg2 = QMessageFactory.createQMessage("Message Number Two!");
		publisherService.setChange(msg2);
		
		boolean expectedResult = true;
		boolean actualResult = publisherService.reAddSubscriber(removedSubscriberId);
		Assert.assertEquals(expectedResult, actualResult);
		
		removedSubscriberId = 1;
		expectedResult = false;
		publisherService.removeSubscriber(subscriberOne);
		actualResult = publisherService.reAddSubscriber(removedSubscriberId);
		actualResult = publisherService.reAddSubscriber(removedSubscriberId);
		Assert.assertEquals(expectedResult, actualResult);
	}

}
