package com.everything.twitter.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.event.Observes;
import roboguice.inject.InjectView;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.everything.twitter.R;
import com.everything.twitter.adapter.TwitterAdapter;
import com.everything.twitter.common.CommonApplication;
import com.everything.twitter.events.ResultsReceivedEvent;
import com.everything.twitter.events.TextChangedEvent;
import com.everything.twitter.interfaces.ILogic;
import com.everything.twitter.logic.TwitterLogic.TwitterLogic;
import com.everything.twitter.simple.Mngr;
import com.everything.twitter.views.EverythingEditTextView;

public class MainActivity extends RoboActivity {
	ILogic twitterLogic = null;
	// List<Status> items;

	TwitterAdapter adapter;

	@InjectView(tag = "searchBar")
	EverythingEditTextView et;

	@InjectView(tag = "tweetList")
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Mngr mngr = Mngr.getInstance();
		if (mngr.getItems() == null) {
			mngr.setItems(new ArrayList<Status>());
		}
		if (mngr.getTwitter() == null) {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
					.setOAuthConsumerKey("8v7aZg4PMQ66TbS6ghYjFw")
					.setOAuthConsumerSecret(
							"RBAc4pq2VUmNyw4RQ6foVyoUedMEPWk7kAPv0iQ")
					.setOAuthAccessToken(
							"45514224-o9XiTql4jv2SBovq2lYRJcogHTTmNpwSkpZRVf0Xt")
					.setOAuthAccessTokenSecret(
							"UHx9nMAM3EtWGKEu0OPrYKLAPwKHjOG8tH1rAn08");
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();
			mngr.setTwitter(twitter);
		}
		if (CommonApplication.getInstance().getEventManager() == null) {
			CommonApplication.getInstance().setEventManager(eventManager);
		}

		et.setHandler(new Handler(Looper.getMainLooper()));

		adapter = new TwitterAdapter(getBaseContext(), mngr.getItems());
		list.setAdapter(adapter);
		list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
				case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					list.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getCurrentFocus()
							.getWindowToken(), 0);
					break;

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void onTextchanged(@Observes TextChangedEvent textChanged) {
		if (twitterLogic == null) {
			twitterLogic = new TwitterLogic();
		}
		twitterLogic.query(textChanged.getNewText());
	}

	protected void onResultsReceived(
			@Observes ResultsReceivedEvent<Status> result) {
		List<Status> items = Mngr.getInstance().getItems();
		items.clear();
		if (result.getItems() != null) {
			for (Status status : result.getItems()) {
				items.add(status);
			}
		}
		adapter.notifyDataSetChanged();
		et.hideProgressBar();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		et.setIsReactToTextChange(false);
	}
}
