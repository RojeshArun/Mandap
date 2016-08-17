package com.mandap;

import com.utils.UserDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class SettingsActivity extends BaseActivity implements OnClickListener {

	private ImageView mbtnAutoLogin;

	private FrameLayout mbtnMyProfile;
	private FrameLayout mbtnChangePassword;
	private FrameLayout mbtnAboutApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_settings);
		setTopbarTitle(getString(R.string.settings));
		getLayoutReferences();
		setOnClickListeners();
	}

	private void setOnClickListeners() {
		mbtnAboutApplication.setOnClickListener(this);
		mbtnChangePassword.setOnClickListener(this);
		mbtnMyProfile.setOnClickListener(this);
		mbtnAutoLogin.setOnClickListener(this);
		if (UserDetails.getInstance(this).getAutoLogin()) {
			mbtnAutoLogin.setImageResource(R.drawable.on);
		} else {
			mbtnAutoLogin.setImageResource(R.drawable.off);
		}
	}

	private void getLayoutReferences() {
		mbtnMyProfile = (FrameLayout) findViewById(R.id.btnMyProfile);
		mbtnChangePassword = (FrameLayout) findViewById(R.id.btnChangePassword);
		mbtnAboutApplication = (FrameLayout) findViewById(R.id.btnAboutApplication);

		mbtnAutoLogin = (ImageView) findViewById(R.id.btnAutoLogin);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAboutApplication:
			btnAboutApplicationClicked();
			break;
		case R.id.btnChangePassword:
			btnChangePasswordCliCked();
			break;
		case R.id.btnMyProfile:
			btnMyProfileClicked();
			break;
		case R.id.btnAutoLogin:
			btnAutoLoginClicked();
			break;

		default:
			break;
		}
	}

	private void btnAutoLoginClicked() {
		if (UserDetails.getInstance(this).getAutoLogin()) {
			mbtnAutoLogin.setImageResource(R.drawable.off);
			UserDetails.getInstance(this).setAutoLogin(false);
		} else {
			mbtnAutoLogin.setImageResource(R.drawable.on);
			UserDetails.getInstance(this).setAutoLogin(true);
		}
	}

	private void btnMyProfileClicked() {
		Intent mIntent = new Intent(this, MyProfileActivity.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
	}

	private void btnChangePasswordCliCked() {
		Intent mIntent = new Intent(this, ChangePasswordActivity.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
	}

	private void btnAboutApplicationClicked() {
		Intent mIntent = new Intent(this, AboutApplicationActivity.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
	}

}
