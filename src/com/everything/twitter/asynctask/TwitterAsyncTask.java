package com.everything.twitter.asynctask;

import twitter4j.Query;
import twitter4j.QueryResult;
import android.os.AsyncTask;

import com.everything.twitter.common.CommonApplication;
import com.everything.twitter.events.ResultsReceivedEvent;
import com.everything.twitter.web.TwitterCommunicator;

public class TwitterAsyncTask extends AsyncTask<Query, Void, QueryResult> {

	public TwitterAsyncTask() {
	}

	@Override
	protected QueryResult doInBackground(Query... params) {

		QueryResult retVal = TwitterCommunicator.getData(params[0]);

		return retVal;
	}

	@Override
	protected void onPostExecute(QueryResult result) {
		ResultsReceivedEvent event = new ResultsReceivedEvent(result);

		CommonApplication.getInstance().fireEvent(event);
	}
}
