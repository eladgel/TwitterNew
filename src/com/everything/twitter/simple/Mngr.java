package com.everything.twitter.simple;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Looper;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Mngr {

	public static final String TAG = "Mngr";

	public final static int SPEECH_REQUEST_CODE = 1234;

	private Twitter twitter;

	private static Mngr mInstance;

	private List<Status> mItems;

	private Handler mHandler;

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
		if (twitter == null) {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
					.setOAuthConsumerKey("8v7aZg4PMQ66TbS6ghYjFw")
					.setOAuthConsumerSecret(
							"RBAc4pq2VUmNyw4RQ6foVyoUedMEPWk7kAPv0iQ")
					.setOAuthAccessToken(
							"45514224-o9XiTql4jv2SBovq2lYRJcogHTTmNpwSkpZRVf0Xt")
					.setOAuthAccessTokenSecret(
							"UHx9nMAM3EtWGKEu0OPrYKLAPwKHjOG8tH1rAn08");
			TwitterFactory tf = new TwitterFactory(cb.build());
			twitter = tf.getInstance();
		}

		return twitter;
	}


	public List<Status> getItems() {
		if (mItems == null) {
			mItems = new ArrayList<Status>();
		}
		
		return mItems;
	}

	public Handler getHandler() {
		if(mHandler == null)
		{
			mHandler = new Handler(Looper.getMainLooper());
		}
		
		return mHandler;
	}

//	public void setItems(List<Status> mItems) {
//		this.mItems = mItems;
//	}

}