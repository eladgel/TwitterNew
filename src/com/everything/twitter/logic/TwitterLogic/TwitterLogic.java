package com.everything.twitter.logic.TwitterLogic;

import com.everything.twitter.asynctask.TwitterAsyncTask;
import com.everything.twitter.interfaces.ILogic;

public class TwitterLogic implements ILogic{
	private TwitterAsyncTask currTask;
	@Override
	public void query(String string) {
		if(currTask != null && currTask.ready == false)
		{
			currTask.cancel(true);
		}
		currTask = new TwitterAsyncTask();
		currTask.execute(string);		
	}

}
