package com.everything.twitter.simple;

import twitter4j.Twitter;


public class Mngr {

	public static final String TAG = "Mngr";

	public final static int SPEECH_REQUEST_CODE = 1234;
	
	Twitter twitter;

	public Twitter getTwitter() {
		return twitter;
	}

	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

	private static Mngr mInstance;
	
	public static Mngr getInstance() {
		if (mInstance == null) {
			synchronized (Mngr.class) {
				if (mInstance == null) {
					mInstance = new Mngr();
				}
			}
		}

		return mInstance;
	}

	private Mngr() {
		super();
	}

}