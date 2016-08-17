package com.mandap;

import com.utils.UserDetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

public class LoadingActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		initiateHandler();
	}

	private void initiateHandler() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (UserDetails.getInstance(LoadingActivity.this)
						.getAutoLogin()) {
					navigateToHomeActivity();
				} else {
					navigateToLoginActivity();
				}

			}

		}, 1000);
	}

	private void navigateToLoginActivity() {
		Intent mIntent = new Intent(this, LoginActivity.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
		finish();
	}

	private void navigateToHomeActivity() {
		Intent mIntent = new Intent(this, HomeActivity.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
		finish();
	}
}
