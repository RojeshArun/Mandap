package com.mandap;

import android.os.Bundle;

public class RequestDetailsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		addContentView(R.layout.activity_request_details);
		setTopbarTitle(getString(R.string.request_details));
		getLayoutReferences();
		setClickListeners();
		initiateMyRequestsWS();

	}

	private void initiateMyRequestsWS() {
		// TODO Auto-generated method stub

	}

	private void setClickListeners() {
		// TODO Auto-generated method stub

	}

	private void getLayoutReferences() {
		// TODO Auto-generated method stub

	}

}
