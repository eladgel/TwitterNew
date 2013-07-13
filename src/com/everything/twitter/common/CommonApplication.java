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

	public void fireEvent(final Object event) {
		new Thread() {

			@Override
			public void run() {
				if (eventManager != null)
					eventManager.fire(event);

			}
		}.start();

	}
}
