package com.messaging.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;




import com.messaging.mediator.QMessageController;
import com.messaging.model.QMessage;
import com.messaging.publisher.IPublisher;
import com.messaging.publisher.MessagePublisher;
import com.messaging.subscriber.ISubscriber;
import com.messaging.subscriber.MessageDisplaySubscriber;

public class MultiThreadTestCase {

	private static ExecutorService executor;
	private final static int NUM_THREADS = 2;	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		executor = Executors.newFixedThreadPool(NUM_THREADS);
		
		//1) Create a controller.
		final QMessageController controller = new QMessageController();
		
		//2) Create a two publishers and attach them to the controller.
		final String publisherOne = "Publisher1";
		final String publisherTwo = "Publisher2";
		
		IPublisher<QMessage> publisherA = new MessagePublisher<QMessage>();
		IPublisher<QMessage> publisherB = new MessagePublisher<QMessage>();
		
		controller.addPulisher(publisherA, publisherOne);
		controller.addPulisher(publisherB, publisherTwo);
		
		//3) Create subscribers and attach them to publishers.
		final ISubscriber<QMessage> subscriberOne = new MessageDisplaySubscriber<QMessage>();
		final ISubscriber<QMessage> subscriberTwo = new MessageDisplaySubscriber<QMessage>();
		final ISubscriber<QMessage> subscriberThree = new MessageDisplaySubscriber<QMessage>();
		final ISubscriber<QMessage> subscriberFour = new MessageDisplaySubscriber<QMessage>();
		final ISubscriber<QMessage> subscriberFive = new MessageDisplaySubscriber<QMessage>();
		
		final long subscriberId1 = controller.subscribe(subscriberOne, publisherOne);		
		final long subscriberId2 = controller.subscribe(subscriberTwo, publisherTwo);		
		final long subscriberId3 = controller.subscribe(subscriberThree, publisherTwo);
		final long subscriberId5 = controller.subscribe(subscriberFive, publisherOne);
		
		//4) Creating and running Thread (1). And posting a message before adding a third subscriber in publisher two.
		final String msg = "Come on Please Work!";		
		executor.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("Running Thread (1)");
				//4-a) Unsubscribed subscriber number Three.
				controller.unsubscribe(subscriberThree, publisherTwo);
				//4-b) Only one subscriber will receive the message, subscriber number two.
				controller.postMessage(msg, publisherTwo);				
			}
		});
		
		final String newMsg = "New Message!";
		//5) Creating and running Thread (2). And posting a new message after a slightly delay.
		executor.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("Running Thread (2)");
				try {
					TimeUnit.SECONDS.sleep(1);
					//5-a) Attaching a new subscriber to publisher Two.
					controller.subscribe(subscriberFour, publisherTwo);	
					//5-b) Only two subscribers will receive the message, subscriber number two and four.
					controller.postMessage(newMsg, publisherTwo);
					TimeUnit.SECONDS.sleep(1);
					//5-c) Re-subscribe subscriber number three, and display the two messages he missed.
					controller.resubscribe(subscriberId3, publisherTwo);
				} catch (InterruptedException e) {
					System.out.println("Exception: "+e.getMessage());
				}
			}
		});
		
		//6) Shutting down the executer.
		executor.shutdown();
	}
	
}
