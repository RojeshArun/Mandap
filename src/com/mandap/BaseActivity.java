package com.mandap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity {

	private FrameLayout mMainLayout;
	private FrameLayout mTopBarLayout;

	private TextView mtxtTopbarTitle;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.base_activity);
		getAllReferences();
	}

	private void getAllReferences() {
		mMainLayout = (FrameLayout) findViewById(R.id.mainLayout);
		mTopBarLayout = (FrameLayout) findViewById(R.id.layoutTopbar);
		mtxtTopbarTitle = (TextView) findViewById(R.id.txtTitle);
	}

	public void addContentView(int id) {
		View mContentView = LayoutInflater.from(this).inflate(id, null);
		mMainLayout.addView(mContentView);
	}

	public void hideTopbar() {
		mTopBarLayout.setVisibility(View.GONE);
	}

	public void setTopbarTitle(String mTitle) {
		mtxtTopbarTitle.setText(mTitle);
	}

	protected Toast toast;

	/*
	 * 
	 * Common Toast
	 */
	public void showToast(String msg) {
		if (toast != null)
			toast.cancel();
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.custom_toast,
				(ViewGroup) findViewById(R.id.toast_layout_root));
		TextView text = (TextView) layout.findViewById(R.id.textAlert);
		text.setText(msg);
		toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		if (!(TextUtils.isEmpty(msg)))
			toast.show();
	}

	public void showToast(String message, Object... params) {
		showToast(String.format(message, params));
	}

	public void showToast(int message, Object... params) {
		showToast(String.format(getString(message), params));
	}

	/*
	 * 
	 * Common Progress
	 */

	public void showProgress() {
		((LinearLayout) findViewById(R.id.loading_view))
				.setVisibility(View.VISIBLE);
	}

	public void hideProgress() {
		((LinearLayout) findViewById(R.id.loading_view))
				.setVisibility(View.GONE);
	}

	public void dismissKeyboard(View mView) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
	}

}
