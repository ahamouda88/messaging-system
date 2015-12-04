package com.messaging.subscriber;

public class MessageDisplaySubscriber<T> implements ISubscriber<T>{

	@Override
	public void update(T change) {
		if(change != null){
			displayMessage(change.toString());
		}
	}
	
	private void displayMessage(String msg){
		System.out.println(msg);
	}
	
}
