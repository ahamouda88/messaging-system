package com.messaging.publisher;

import java.util.HashSet;
import java.util.Set;

import com.messaging.subscriber.ISubscriber;
import com.messaging.util.ParametersUtil;

public class MessagePublisher<T> implements IPublisher<T>{

	// Used a Set to remove duplicates.
	private final Set<ISubscriber<T>> subscribers;
	private boolean changed;
	
	public MessagePublisher() {
		subscribers = new HashSet<ISubscriber<T>>();
		changed = false;
	}
	
	@Override
	public synchronized void addSubscriber(ISubscriber<T> subscriber) {
		ParametersUtil.checkNullParameters(subscriber);
		subscribers.add(subscriber);
	}

	@Override
	public synchronized void removeSubscriber(ISubscriber<T> subscriber) {
		ParametersUtil.checkNullParameters(subscriber);
		subscribers.remove(subscriber);	
	}

	@Override
	public synchronized void notifyAllSubscribers(T change) {
		if(changed){	
			for(ISubscriber<T> s : subscribers){
				s.update(change);
			}
			changed = false;
		}
	}

	@Override
	public void notifySubscriber(ISubscriber<T> subscriber, T change) {
		ParametersUtil.checkNullParameters(subscriber, change);
		subscriber.update(change);
	}
	
	@Override
	public synchronized void setChange(T change) {
		ParametersUtil.checkNullParameters(change);
		changed = true;
		notifyAllSubscribers(change);		
	}

	@Override
	public synchronized long getNoOfSubscribers() {
		return subscribers.size();
	}

	@Override
	public synchronized boolean containSubscriber(ISubscriber<T> subscriber) {
		ParametersUtil.checkNullParameters(subscriber);
		return subscribers.contains(subscriber);
	}

}
