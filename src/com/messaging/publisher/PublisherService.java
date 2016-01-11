package com.messaging.publisher;

import java.util.HashMap;
import java.util.Map;

import com.messaging.subscriber.ISubscriber;

public class PublisherService<T> {
	
	// Used to keep track of all subscribers registered and unregistered to the publisher.
	private final Map<Long, ISubscriber<T>> allSubscribers;
	// Used to keep track of the last change id of unregistered subscriber.
	private final Map<ISubscriber<T>, Long> subscriberLastChangeId;
	// Used to keep track of all messages.
	private final Map<Long, T> historicalChanges;
	private final IPublisher<T> publisher;
	private long nxtSubscriberId;
	private long nxtChangeId;
	
	public PublisherService(IPublisher<T> publisher) {
		this.publisher = publisher;
		allSubscribers = new HashMap<Long, ISubscriber<T>>();
		subscriberLastChangeId = new HashMap<ISubscriber<T>, Long>();
		historicalChanges = new HashMap<Long, T>();
		
	}

	public synchronized long addSubscriber(ISubscriber<T> subscriber) {
		long subscriberId = -1;
		// Check if subscriber already registered to the publisher.
		if(!publisher.containSubscriber(subscriber)){
			// Check if subscriber is previously registered.
			if(!isPrevSubscriber(subscriber)){
				nxtSubscriberId++;
				publisher.addSubscriber(subscriber);
				allSubscribers.put(nxtSubscriberId, subscriber);
				subscriberId = nxtSubscriberId;
			}
		}
		return subscriberId;
	}

	public synchronized void removeSubscriber(ISubscriber<T> subscriber) {		
		if(publisher.containSubscriber(subscriber)){
			publisher.removeSubscriber(subscriber);
			long changeId = nxtChangeId > 0 ? nxtChangeId : 0;
			// Keep track of the last change notified for this removed subscriber.
			subscriberLastChangeId.put(subscriber, changeId);
		}
	}

	public synchronized boolean reAddSubscriber(long subscriberId) {
		boolean check = false;
		ISubscriber<T> subscriber = allSubscribers.get(subscriberId);
		
		// Check if subscriber is already registered to the publisher, 
		// and if we have an old record for this subscriber.
		if(subscriber != null && !publisher.containSubscriber(subscriber)){
			publisher.addSubscriber(subscriber);
			check = updateForPreviousChanges(subscriber);
		}
		return check;
	}

	private boolean updateForPreviousChanges(ISubscriber<T> subscriber){
		boolean check = false;
		Long lastChangeId = subscriberLastChangeId.get(subscriber);
		if(lastChangeId != null){
			while(lastChangeId < nxtChangeId){
				T change = historicalChanges.get(lastChangeId);
				publisher.notifySubscriber(subscriber, change);
				lastChangeId++;
			}
			subscriberLastChangeId.put(subscriber, 0l);
			check = true;
		}
		return check;
	}
	
	public synchronized void setChange(T change) {
		if(change != null){			
			publisher.setChange(change);
			historicalChanges.put(nxtChangeId, change);
			nxtChangeId++;
		}
	}
	
	public synchronized void clearHistoricalChanges(){
		historicalChanges.clear();
	}
	
	private boolean isPrevSubscriber(ISubscriber<T> subscriber){
		boolean result = false;
		Long checkValidSubscriber = subscriberLastChangeId.get(subscriber);
		if(checkValidSubscriber != null && checkValidSubscriber != 0){
			result = true;
		}
		return result;
	}
}
