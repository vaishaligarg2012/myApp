package com.hindustantimes.SubscriptionPortal.Exceptions;

@SuppressWarnings("serial")
public class ResponseToUser extends RuntimeException{
    public ResponseToUser(final String msg) {
		super(msg);
	}
}
   