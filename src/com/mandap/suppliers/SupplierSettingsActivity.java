package com.mandap.suppliers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mandap.BaseActivity;
import com.mandap.LoginActivity;
import com.mandap.R;
import com.utils.UserDetails;

public class SupplierSettingsActivity extends BaseActivity implements
		OnClickListener {

	private ImageView mbtnAutoLogin;
	private FrameLayout mbtnLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_supplier_settings);
		setTopbarTitle(getString(R.string.settings));
		getLayoutReferences();
		setOnClickListeners();
	}

	private void setOnClickListeners() {
		mbtnLogout.setOnClickListener(this);
		mbtnAutoLogin.setOnClickListener(this);
		if (UserDetails.getInstance(this).getSupplierAutoLogin()) {
			mbtnAutoLogin.setImageResource(R.drawable.on);
		} else {
			mbtnAutoLogin.setImageResource(R.drawable.off);
		}
	}

	private void getLayoutReferences() {
		mbtnLogout = (FrameLayout) findViewById(R.id.btnMyProfile);
		mbtnAutoLogin = (ImageView) findViewById(R.id.btnAutoLogin);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnMyProfile:
			btnLogoutClicked();
			break;
		case R.id.btnAutoLogin:
			btnAutoLoginClicked();
			break;
		default:
			break;
		}
	}

	private void btnAutoLoginClicked() {
		if (UserDetails.getInstance(this).getSupplierAutoLogin()) {
			mbtnAutoLogin.setImageResource(R.drawable.off);
			UserDetails.getInstance(this).setSupplierAutoLogin(false);
		} else {
			mbtnAutoLogin.setImageResource(R.drawable.on);
			UserDetails.getInstance(this).setSupplierAutoLogin(true);
		}
	}

	private void btnLogoutClicked() {
		Intent mIntent = new Intent(this, LoginActivity.class);
		mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(mIntent);
	}
}
