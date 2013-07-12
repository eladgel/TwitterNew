package com.everything.twitter.logic.TwitterLogic;

import java.util.Locale;

import twitter4j.Query;
import twitter4j.QueryResult;

import com.everything.twitter.asynctask.TwitterAsyncTask;
import com.everything.twitter.common.Consts;
import com.everything.twitter.interfaces.ILogic;

public class TwitterLogic implements ILogic {
	private boolean isLoadingMore;
	private QueryResult lastResult;

	private TwitterAsyncTask currTask;

	@Override
	public void query(String string) {
		setLoadingMore(false);
		if (currTask != null && currTask.ready == false) {
			currTask.cancel(true);
		}
		currTask = new TwitterAsyncTask();
		if (string.equals(Consts.EMPTY_STRING) == false) {
			Query query = new Query(string);
			query.setLocale(Locale.getDefault().toString());
			currTask.execute(query);
		}
	}

	@Override
	public void getNextPage() {
		setLoadingMore(true);
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

	public boolean isLoadingMore() {
		return isLoadingMore;
	}

	public void setLoadingMore(boolean isLoadingMore) {
		this.isLoadingMore = isLoadingMore;
	}

}
