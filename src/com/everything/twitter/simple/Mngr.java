package com.everything.twitter.simple;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;

public class Mngr {

	public static final String TAG = "Mngr";

	public final static int SPEECH_REQUEST_CODE = 1234;

	private Twitter twitter;

	private static Mngr mInstance;

	private List<Status> mItems;
	
	private Mngr() {
		super();
	}

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

	public Twitter getTwitter() {
		return twitter;
	}

	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

	public List<Status> getItems() {
		return mItems;
	}

	public void setItems(List<Status> mItems) {
		this.mItems = mItems;
	}

}