package com.everything.twitter.events;

import twitter4j.QueryResult;
//import com.base.fufinder.enums.itemearchType;

public class ResultsReceivedEvent{

	QueryResult mItem;


	public ResultsReceivedEvent(QueryResult item) {
		setItem(item);
	}

	public QueryResult getItem() {
		return mItem;
	}

	public void setItem(QueryResult item) {
		mItem = item;
	}


}
