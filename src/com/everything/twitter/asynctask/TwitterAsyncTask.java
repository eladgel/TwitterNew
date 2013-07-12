package com.everything.twitter.asynctask;

import java.util.List;

import android.os.AsyncTask;

import com.everything.twitter.common.CommonApplication;
import com.everything.twitter.events.ResultsReceivedEvent;
import com.everything.twitter.web.client.TwitterCommunicator;

public class TwitterAsyncTask extends
		AsyncTask<String, Void, List<twitter4j.Status>> {
	public boolean ready = false;

	public TwitterAsyncTask() {
	}

	@Override
	protected List<twitter4j.Status> doInBackground(String... params) {
		List<twitter4j.Status> retVal = TwitterCommunicator.getData(params[0]);

		return retVal;
	}

	@Override
	protected void onPostExecute(List<twitter4j.Status> result) {
		ResultsReceivedEvent<twitter4j.Status> event = new ResultsReceivedEvent<twitter4j.Status>(
				result);
		CommonApplication.getInstance().fireEvent(event);
	}
}
