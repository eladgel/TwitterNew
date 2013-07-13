package com.everything.twitter.web;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.everything.twitter.simple.Model;

public class TwitterCommunicator {

	public static QueryResult getData(Query query) {
		QueryResult retVal = null;
		Twitter twitter = Model.getInstance().getTwitter();
		try {
			retVal = twitter.search(query);
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return retVal;
	}
}
