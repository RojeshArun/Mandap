package com.mandap.suppliers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.mandap.BaseActivity;
import com.mandap.EditProfile;
import com.mandap.R;

public class SuppliersHome extends BaseActivity implements OnClickListener {

	private LinearLayout mbtnMyProducts, mbtnAddProduct, mbtnManageProfile,
			mbtnSettings, mbtnOrders;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		addContentView(R.layout.activity_suppliers_home);
		hideTopbar();
		getLayoutRefernces();
		setOnClickListeners();
	}

	private void setOnClickListeners() {
		mbtnMyProducts.setOnClickListener(this);
		mbtnManageProfile.setOnClickListener(this);
		mbtnAddProduct.setOnClickListener(this);
		mbtnSettings.setOnClickListener(this);
		mbtnOrders.setOnClickListener(this);
	}

	private void getLayoutRefernces() {
		mbtnMyProducts = (LinearLayout) findViewById(R.id.btnMyProducts);
		mbtnAddProduct = (LinearLayout) findViewById(R.id.btnAddProduct);
		mbtnManageProfile = (LinearLayout) findViewById(R.id.btnManageProfile);
		mbtnSettings = (LinearLayout) findViewById(R.id.btnSettings);
		mbtnOrders = (LinearLayout) findViewById(R.id.btnOrders);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnMyProducts:
			btnMyProductsClicked();
			break;
		case R.id.btnAddProduct:
			btnAddProductClicked();
			break;
		case R.id.btnManageProfile:
			btnManageProfileClicked();
			break;
		case R.id.btnOrders:
			btnOrdersClicked();
			break;
		case R.id.btnSettings:
			btnSettingsClicked();
			break;
		default:
			break;
		}
	}

	private void btnSettingsClicked() {
		Intent mIntent = new Intent(this, SupplierSettingsActivity.class);
		startActivity(mIntent);
	}

	private void btnMyProductsClicked() {
		Intent mIntent = new Intent(this, SupplierProductsActivity.class);
		startActivity(mIntent);
	}

	private void btnAddProductClicked() {
		Intent mIntent = new Intent(this, AddProductActivity.class);
		startActivity(mIntent);
	}

	private void btnManageProfileClicked() {
		Intent mIntent = new Intent(this, EditProfile.class);
		startActivity(mIntent);
	}

	private void btnOrdersClicked() {
		Intent mIntent = new Intent(this, SupplierOrdersActivity.class);
		startActivity(mIntent);
	}

}
