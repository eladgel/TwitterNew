package com.everything.twitter.activity;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.event.Observes;
import roboguice.inject.InjectView;
import twitter4j.Status;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.everything.twitter.R;
import com.everything.twitter.adapter.TwitterAdapter;
import com.everything.twitter.common.CommonApplication;
import com.everything.twitter.common.Consts;
import com.everything.twitter.events.ResultsReceivedEvent;
import com.everything.twitter.events.TextChangedEvent;
import com.everything.twitter.logic.TwitterLogic.TwitterLogic;
import com.everything.twitter.simple.Model;
import com.everything.twitter.views.EverythingEditTextView;

public class MainActivity extends RoboActivity {

	TwitterAdapter adapter;

	@InjectView(tag = "searchBar")
	EverythingEditTextView et;

	@InjectView(tag = "tweetList")
	ListView listView;

	final Model model = Model.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (CommonApplication.getInstance().getEventManager() == null) {
			CommonApplication.getInstance().setEventManager(eventManager);
		}

		et.setHandler(model.getHandler());

		adapter = new TwitterAdapter(getBaseContext(), model.getItems());
		listView.setAdapter(adapter);
		listView.setOnScrollListener(new OnScrollListener() {
			int lastVisiblePositionAcquired = 0;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
				case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					listView.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getCurrentFocus()
							.getWindowToken(), 0);
					break;
				case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
					if (listView.getLastVisiblePosition() >= listView
							.getCount() - 3
							&& listView.getLastVisiblePosition() > lastVisiblePositionAcquired) {
						lastVisiblePositionAcquired = listView
								.getLastVisiblePosition();
						if (model.getTwitterLogic().getLastResult().hasNext()) {
							et.showProgressBar();
							model.getTwitterLogic().getNextPage();
						}
					}
				default:
					break;
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void onTextchanged(@Observes TextChangedEvent textChanged) {
		Model model = Model.getInstance();
		if (textChanged.getNewText().equals(Consts.EMPTY_STRING) == true) {
			model.getItems().clear();
			resultsUpdated();
		} else {

			model.getTwitterLogic().query(textChanged.getNewText());
		}
	}

	protected void onResultsReceived(@Observes ResultsReceivedEvent result) {
		if (et.getText().toString().equals(result.getItem().getQuery()) == false) {
			return;
		}

		Model model = Model.getInstance();

		List<Status> items = model.getItems();

		if (model.getTwitterLogic().isLoadingMore() == false) {
			items.clear();
		}
		if (result.getItem() != null) {

			for (Status status : result.getItem().getTweets()) {
				items.add(status);
			}
			model.getTwitterLogic().setLastResult(result.getItem());
		}
		resultsUpdated();

	}

	private void resultsUpdated() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				adapter.notifyDataSetChanged();
				et.hideProgressBar();
			}
		});
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		et.setIsReactToTextChange(false);
	}
}
