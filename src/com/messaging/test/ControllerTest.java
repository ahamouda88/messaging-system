package com.messaging.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.messaging.mediator.QMessageController;
import com.messaging.model.QMessage;
import com.messaging.publisher.IPublisher;
import com.messaging.publisher.MessagePublisher;
import com.messaging.subscriber.ISubscriber;
import com.messaging.subscriber.MessageDisplaySubscriber;

public class ControllerTest {

	private QMessageController controller;
	private ISubscriber<QMessage> subscriberOne;
	private ISubscriber<QMessage> subscriberTwo;
	private ISubscriber<QMessage> subscriberThree;
	private ISubscriber<QMessage> subscriberFour;
	private String publisherOne;
	private String publisherTwo;
	
	@Before
	public void testAddingSubscribers(){
		controller = new QMessageController();
		
		publisherOne = "Publisher1";
		publisherTwo = "Publisher2";
		
		IPublisher<QMessage> publisherA = new MessagePublisher<QMessage>();
		IPublisher<QMessage> publisherB = new MessagePublisher<QMessage>();
		
		controller.addPulisher(publisherA, publisherOne);
		controller.addPulisher(publisherB, publisherTwo);
		
		subscriberOne = new MessageDisplaySubscriber<QMessage>();
		subscriberTwo = new MessageDisplaySubscriber<QMessage>();
		subscriberThree = new MessageDisplaySubscriber<QMessage>();
		subscriberFour = new MessageDisplaySubscriber<QMessage>();
		
		long expectedId = 1;
		long resultId = controller.subscribe(subscriberOne, publisherOne);
		Assert.assertEquals(expectedId, resultId);
		
		expectedId = 1;
		resultId = controller.subscribe(subscriberTwo, publisherTwo);
		Assert.assertEquals(expectedId, resultId);
		
		expectedId = 2;
		resultId = controller.subscribe(subscriberThree, publisherTwo);
		Assert.assertEquals(expectedId, resultId);
		
		expectedId = 3;
		resultId = controller.subscribe(subscriberFour, publisherTwo);
		Assert.assertEquals(expectedId, resultId);
	}
	
	@Test
	public void testRemoveAndReAddSubscribers() {
		controller.unsubscribe(subscriberThree, publisherTwo);
		String msg = "Good Job!";
		controller.postMessage(msg, publisherTwo);
		
		boolean expectedResult = true;
		boolean actualResult = controller.resubscribe(2, publisherTwo);
		Assert.assertEquals(expectedResult, actualResult);
		
		controller.postMessage(msg, publisherOne);
	}
}
