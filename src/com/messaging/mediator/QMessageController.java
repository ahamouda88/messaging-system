package com.messaging.mediator;

import java.util.HashMap;
import java.util.Map;

import com.messaging.factory.QMessageFactory;
import com.messaging.model.QMessage;
import com.messaging.publisher.IPublisher;
import com.messaging.publisher.PublisherService;
import com.messaging.subscriber.ISubscriber;
import com.messaging.util.ParametersUtil;

public class QMessageController {
	
	private final Map<String, PublisherService<QMessage>> publisherMap;
	
	public QMessageController(){
		publisherMap = new HashMap<String, PublisherService<QMessage>>();
	}
	
	public synchronized long subscribe(ISubscriber<QMessage> subscriber, String publisherName) {
		long subscriberId = -1;
		ParametersUtil.checkNullParameters(subscriber, publisherName);
		
		PublisherService<QMessage> publisherService = publisherMap.get(publisherName);
		if(publisherService != null){
			subscriberId = publisherService.addSubscriber(subscriber);
		}
		return subscriberId;
	}
	
	public synchronized void unsubscribe(ISubscriber<QMessage> subscriber, String publisherName){
		ParametersUtil.checkNullParameters(subscriber, publisherName);
		
		PublisherService<QMessage> publisherService = publisherMap.get(publisherName);
		if(publisherService != null){
			publisherService.removeSubscriber(subscriber);
		}
	}
	
	public synchronized boolean resubscribe(long subcriberId, String publisherName){
		boolean result = false;
		ParametersUtil.checkNullParameters(subcriberId, publisherName);
		
		PublisherService<QMessage> publisherService = publisherMap.get(publisherName);		
		if(publisherService != null){
			result = publisherService.reAddSubscriber(subcriberId);
		}
		return result;
	}
	
	public synchronized void postMessage(String message, String publisherName){
		ParametersUtil.checkNullParameters(message, publisherName);
		
		PublisherService<QMessage> publisherService = publisherMap.get(publisherName);	
		QMessage newMessage = QMessageFactory.createQMessage(message);
		
		if(publisherService != null){
			publisherService.setChange(newMessage);
		}
	}
	
	public synchronized void addPulisher(IPublisher<QMessage> publisher, String publisherName){
		ParametersUtil.checkNullParameters(publisher, publisherName);
		
		PublisherService<QMessage> publisherService = new PublisherService<QMessage>(publisher);
		publisherMap.put(publisherName, publisherService);
	}
}
