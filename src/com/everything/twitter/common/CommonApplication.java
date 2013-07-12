package com.everything.twitter.common;

import roboguice.event.EventManager;

public class CommonApplication {

	protected EventManager eventManager;
	// private boolean isKeyboardOpen = true;
	private static CommonApplication mInstance;

	public static CommonApplication getInstance() {
		if (mInstance == null) {
			synchronized (CommonApplication.class) {
				if (mInstance == null) {
					mInstance = new CommonApplication();
				}
			}
		}

		return mInstance;
	}

	public void setEventManager(EventManager mngr) {
		eventManager = mngr;
	}


	public EventManager getEventManager() {
		return eventManager;
	}

	public void fireEvent(Object event) {
		final Object fired = event;
		new Runnable() {

			@Override
			public void run() {
				if (eventManager != null)
					eventManager.fire(fired);

			}
		}.run();

	}
}
