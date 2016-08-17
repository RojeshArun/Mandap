package com.mandap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class HomeActivity extends BaseActivity implements OnClickListener {

	private LinearLayout mbtnSettings, mbtnMyRequests, mbtnFavouriteSuppliers,
			mbtnBrowseSuppliers, mbtnAdvanceSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_home);
		hideTopbar();
		getLayoutRefernces();
		setOnClickListeners();
	}

	private void setOnClickListeners() {
		mbtnSettings.setOnClickListener(this);
		mbtnFavouriteSuppliers.setOnClickListener(this);
		mbtnMyRequests.setOnClickListener(this);
		mbtnBrowseSuppliers.setOnClickListener(this);
		mbtnAdvanceSearch.setOnClickListener(this);
	}

	private void getLayoutRefernces() {
		mbtnSettings = (LinearLayout) findViewById(R.id.btnSettings);
		mbtnMyRequests = (LinearLayout) findViewById(R.id.btnMyRequests);
		mbtnFavouriteSuppliers = (LinearLayout) findViewById(R.id.btnFavouriteSuppliers);
		mbtnBrowseSuppliers = (LinearLayout) findViewById(R.id.btnBrowseSuppliers);
		mbtnAdvanceSearch = (LinearLayout) findViewById(R.id.btnAdvanceSearch);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSettings:
			btnSettingsClicked();
			break;
		case R.id.btnMyRequests:
			btnMyRequestsClicked();
			break;
		case R.id.btnFavouriteSuppliers:
			btnFavouriteSuppliers();
			break;
		case R.id.btnBrowseSuppliers:
			btnBrowseSuppliers();
			break;
		case R.id.btnAdvanceSearch:
			btnAdvanceSearchClicked();
			break;
		default:
			break;
		}
	}

	private void btnAdvanceSearchClicked() {
		Intent mIntent = new Intent(this, SearchActivity.class);
		startActivity(mIntent);
		overridePendingTransition(R.anim.pop_anim, R.anim.push_anim);
	}

	private void btnBrowseSuppliers() {
		Intent mIntent = new Intent(this, SuppliersActivity.class);
		mIntent.putExtra("isFrom", "browser_suppliers");
		startActivity(mIntent);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
	}

	private void btnFavouriteSuppliers() {
		Intent mIntent = new Intent(this, FavouriteSuppliersActivity.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
	}

	private void btnMyRequestsClicked() {
		Intent mIntent = new Intent(this, MyRequestsActivity.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
	}

	private void btnSettingsClicked() {
		Intent mIntent = new Intent(this, SettingsActivity.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
	}
}
