package com.everything.twitter.views;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.everything.twitter.R;
import com.everything.twitter.common.CommonApplication;
import com.everything.twitter.common.Consts;
import com.everything.twitter.events.TextChangedEvent;

public class EverythingEditTextView extends RelativeLayout {

	protected static final String TAG = "FuEditTextView";

	private EditText mEditText;
	private ProgressBar mProgressBar;
	private TextChangedEvent mTextChangedEvent;

	private Handler mHandler;

	private boolean isReactToTextChange = true;

	public EverythingEditTextView(Context context) {
		super(context);
		init(null, 0);
	}

	public EverythingEditTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public EverythingEditTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	public View getProgressBarView() {
		return mProgressBar;
	}

	public void setHandler(Handler handler) {
		mHandler = handler;
	}

	private void init(AttributeSet attrs, int defStyle) {
		mTextChangedEvent = new TextChangedEvent(null);
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.search_edittext_view, this);
		if (isInEditMode()) {
			return;
		}

		mEditText = (EditText) findViewById(R.id.searchText);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mProgressBar.setIndeterminate(true);
		mProgressBar.setIndeterminateDrawable(getResources().getDrawable(
				R.anim.new_prog));

		TextWatcher textWatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			final Runnable fireText = new Runnable() {
				@Override
				public void run() {
					CommonApplication.getInstance()
							.fireEvent(mTextChangedEvent);
				}
			};

			@Override
			public void afterTextChanged(Editable editable) {
				if (isReactToTextChange == false) {
					isReactToTextChange = true;
					return;
				}

				mHandler.removeCallbacks(fireText);

				String text = mEditText.getText().toString().trim();
				mTextChangedEvent.setNewText(text);

				if (mTextChangedEvent.getNewTextSize() == 0) {
					hideProgressBar();
				} else {
					showProgressBar();
				}

				mHandler.postDelayed(fireText, Consts.DELAY_IN_MILLIS);
			}

		};
		mEditText.addTextChangedListener(textWatcher);
		mEditText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return false;
			}
		});

	}

	public EditText getEditText() {
		return mEditText;
	}

	public void setText(String text, boolean isReactToTextChange) {
		mEditText.setText(text);
	}

	public Editable getText() {
		return mEditText.getText();
	}

	public void setSelection(int index) {
		mEditText.setSelection(index);

	}

	public void showProgressBar() {
		getProgressBarView().setVisibility(View.VISIBLE);
	}

	public void hideProgressBar() {
		getProgressBarView().setVisibility(View.INVISIBLE);
	}

	public void setIsReactToTextChange(boolean isReactToTextChange) {
		this.isReactToTextChange = isReactToTextChange;

	}
}