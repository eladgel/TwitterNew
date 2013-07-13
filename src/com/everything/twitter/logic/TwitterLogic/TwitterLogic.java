package com.everything.twitter.logic.TwitterLogic;

import java.util.Locale;

import twitter4j.Query;
import twitter4j.QueryResult;

import android.os.AsyncTask;

import com.everything.twitter.asynctask.TwitterAsyncTask;
import com.everything.twitter.interfaces.ILogic;

public class TwitterLogic implements ILogic {
	private boolean isLoadingMorePages;
	private QueryResult lastResult;
	private TwitterAsyncTask currTask;

	@Override
	public void query(String queryString) {
		isLoadingMorePages = false;
		if (currTask != null
				&& currTask.getStatus().equals(AsyncTask.Status.FINISHED) == false) {
			currTask.cancel(true);
		}
		currTask = new TwitterAsyncTask();
		
		
		Query query = new Query(queryString);
		query.setLocale(Locale.getDefault().toString());
		currTask.execute(query);

	}

	@Override
	public void getNextPage() {
		isLoadingMorePages = true;
		Query query = lastResult.nextQuery();
		currTask = new TwitterAsyncTask();
		currTask.execute(query);
	}

	public QueryResult getLastResult() {
		return lastResult;
	}

	public void setLastResult(QueryResult lastResult) {
		this.lastResult = lastResult;
	}

	public boolean isLoadingMorePages() {
		return isLoadingMorePages;
	}

//	private void setLoadingMore(boolean isLoadingMore) {
//		this.isLoadingMore = isLoadingMore;
//	}

}
