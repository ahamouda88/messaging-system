package com.messaging.subscriber;

public interface ISubscriber<T> {

	public void update(T change);
}
