package com.everything.twitter.adapter;

import java.util.List;

import twitter4j.Status;
import twitter4j.User;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.everything.twitter.R;
import com.everything.twitter.simple.UsersGetProfilePics;
import com.everything.twitter.simple.Utility;

public class TwitterAdapter extends BaseAdapter {

	private Context mContext;
	private List<Status> mList;

	public TwitterAdapter(Context ctx, List<Status> items) {
		this.mContext = ctx;
		mList = items;
		if (Utility.model == null) {
			Utility.model = new UsersGetProfilePics();
		}
		 Utility.model.setListener(this);
	}

	@Override
	public int getCount() {
		if (mList == null) {
			return 0;
		}

		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		if (mList == null) {
			return null;
		}

		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			v = LayoutInflater.from(mContext).inflate(R.layout.twitter_item,
					null);
			holder = new ViewHolder();
			holder.userName = (TextView) v.findViewById(R.id.twitter_text_name);
			holder.userId = (TextView) v
					.findViewById(R.id.twitter_text_twitterId);
			holder.userProfilePic = (ImageView) v
					.findViewById(R.id.twitter_image_profile);
			holder.tweetDate = (TextView) v
					.findViewById(R.id.twitter_text_date);
			holder.tweetText = (TextView) v
					.findViewById(R.id.twitter_text_result);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		Status status = mList.get(position);
		User user = status.getUser();
		holder.userName.setText(user.getName());
		holder.userId.setText(user.getScreenName());

		Bitmap bit = Utility.model.getImage(String.valueOf(user.getId()),
				user.getProfileImageURL());
		if (bit != null) {
			holder.userProfilePic.setImageBitmap(bit);
		} else {
			holder.userProfilePic
					.setImageResource(R.drawable.ic_twitter_contact);
		}

		holder.tweetDate.setText(status.getCreatedAt().toString());
		holder.tweetText.setText(status.getText());

		return v;
	}

	static class ViewHolder {
		TextView userName;
		TextView userId;
		ImageView userProfilePic;
		TextView tweetDate;
		TextView tweetText;
		int position;
	}

}
