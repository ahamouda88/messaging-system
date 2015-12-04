package com.messaging.publisher;

import com.messaging.subscriber.ISubscriber;

public interface IPublisher<T> {

	public void addSubscriber(ISubscriber<T> subscriber);
	
	public void removeSubscriber(ISubscriber<T> subscriber);
	
	public void notifyAllSubscribers(T change);
	
	public void notifySubscriber(ISubscriber<T> subscriber, T change);
	
	public void setChange(T change);
	
	public long getNoOfSubscribers();
	
	public boolean containSubscriber(ISubscriber<T> subscriber);
	
}
