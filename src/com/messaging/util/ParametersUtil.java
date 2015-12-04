package com.messaging.util;

public final class ParametersUtil {
	
	private ParametersUtil(){}
	
	public static void checkNullParameters(Object... paras){
		if(paras != null){
			for(Object o : paras){
				if(o == null){
					throw new NullPointerException();
				}
			}
		}
	}
}
