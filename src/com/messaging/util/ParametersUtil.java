package com.messaging.util;

/**
 * This utility class is used to operate different functions on a method's parameters.
 */
public final class ParametersUtil {
	
	private ParametersUtil(){}
	
	public static void checkNullParameters(Object... paras){
		synchronized(paras){
			if(paras != null){
				for(Object o : paras){
					if(o == null){
						throw new NullPointerException();
					}
				}
			}
		}
	}
}
