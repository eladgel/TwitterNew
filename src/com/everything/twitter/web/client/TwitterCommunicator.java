package com.everything.twitter.web.client;

import java.util.List;
import java.util.Locale;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import android.util.Log;

import com.everything.twitter.common.Consts;
import com.everything.twitter.simple.Mngr;

public class TwitterCommunicator {

	private static final String TAG = "TwitterCommunicator";

	public static List<Status> getData(String queryString) {
		List<Status> retVal = null;
		Twitter twitter = Mngr.getInstance().getTwitter();// TwitterFactory.getSingleton();
		if (queryString.equals(Consts.EMPTY_STRING) == false) {
			Query query = new Query(queryString);
			query.setLocale(Locale.getDefault().toString());
			QueryResult result;

			try {
				Log.d(TAG, queryString);
				result = twitter.search(query);
				retVal = result.getTweets();
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}

		return retVal;
	}
}
