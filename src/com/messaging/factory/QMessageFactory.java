package com.messaging.factory;

import java.util.Date;
import java.util.Calendar;

import com.messaging.model.QMessage;

public final class QMessageFactory {
	
	private static long messageId;
	
	static{
		messageId = 0;
	}
	
	private QMessageFactory(){}
	
	public synchronized static QMessage createQMessage(String message){
		QMessage newMessage = null;
		if(message != null){
			Calendar cal = Calendar.getInstance();
			Date datePublished = cal.getTime();
			newMessage = new QMessage(messageId, message, datePublished);
			messageId++;
		}
		return newMessage;
	}
}
