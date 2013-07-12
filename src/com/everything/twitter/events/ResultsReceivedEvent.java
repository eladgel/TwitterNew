package com.everything.twitter.events;

import java.util.List;
//import com.base.fufinder.enums.ItemSearchType;

public class ResultsReceivedEvent<T> {

	List<T> mItems;


	public ResultsReceivedEvent(List<T> items) {
		setItems(items);
	}

	public List<T> getItems() {
		return mItems;
	}

	public void setItems(List<T> items) {
		mItems = items;
	}


}
