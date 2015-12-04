package com.messaging.test;

import junit.framework.Assert;

import org.junit.Test;

import com.messaging.factory.QMessageFactory;
import com.messaging.model.QMessage;

public class QMessageFactoryTest {

	@Test
	public void testCreateQMessage(){
		long expectedId = 0;
		String message = "Wats Up!";
		QMessage msg = QMessageFactory.createQMessage(message);		
		Assert.assertEquals(expectedId, msg.getId());

		message = "Not Again!";
		expectedId = 1;
		msg = QMessageFactory.createQMessage(message);
		Assert.assertEquals(expectedId, msg.getId());
	}
}
