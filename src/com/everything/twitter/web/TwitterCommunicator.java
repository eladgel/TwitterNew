package com.everything.twitter.web.client;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.everything.twitter.simple.Mngr;

public class TwitterCommunicator {

	private static final String TAG = "TwitterCommunicator";

	public static QueryResult getData(Query query) {
		QueryResult retVal = null;
		Twitter twitter = Mngr.getInstance().getTwitter();// TwitterFactory.getSingleton();

		// QueryResult result;

		try {
			// Log.d(TAG, queryString);
			retVal = twitter.search(query);
			// retVal = result.getTweets();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		// }

		return retVal;
	}
}
