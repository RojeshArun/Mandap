package com.mandap.suppliers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mandap.BaseActivity;
import com.mandap.BaseApplication;
import com.mandap.R;
import com.utils.CommonUtils;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.WSClass;
import com.utils.CommonUtils.SpinnerCallBack;
import com.utils.MandapHolder;

public class ProductDetailsActivity extends BaseActivity implements
		OnClickListener {

	private TextView meditSelectColor, meditSelectSize, meditSelectQuality;
	private EditText meditBale, meditWeight, meditQuantity, meditGsm,
			meditPriceRetailer, meditWholeSellerPrice;
	private ArrayList<MandapHolder> mColorList;
	private ArrayList<MandapHolder> mSizeList;
	private ArrayList<MandapHolder> mQualityList;

	private MandapHolder mProductDetails;

	private ArrayList<MandapHolder> mProductsArray;
	private String[] mColors;
	private String mProductId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.add_product);
		setTopbarTitle(getString(R.string.product_details));
		getLayoutReferences();
		setClickListeners();
		getData();
		initiateSupplierProductsWS();
	}

	private void getData() {
		if (getIntent() != null && getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey("data")) {
				mProductDetails = (MandapHolder) getIntent().getExtras()
						.getSerializable("data");
				setData();
			}
		}
	}

	private void setData() {
		meditSelectColor.setText(mProductDetails.getProductName());
		meditBale.setText(mProductDetails.getBale());
		meditSelectSize.setText(mProductDetails.getSize());
		meditWeight.setText(mProductDetails.getWeight());
		meditQuantity.setText(mProductDetails.getQuantity());
		meditSelectQuality.setText(mProductDetails.getVirginType());
		meditGsm.setText(mProductDetails.getGsm());
		meditPriceRetailer.setText(mProductDetails.getPriceSeller());
		meditWholeSellerPrice.setText(mProductDetails.getPriceWholeSeller());
	}

	private void setClickListeners() {
		meditSelectColor.setOnClickListener(this);
		meditSelectQuality.setOnClickListener(this);
		meditSelectSize.setOnClickListener(this);

	}

	private void btnSelectColorClicked() {

		new CommonUtils().SingleChoice("Select Color", this, mColors,
				meditSelectColor, new SpinnerCallBack() {

					@Override
					public void onItemSelected(int pos) {
						meditSelectColor.setText(mColors[pos]);
						mProductId = mProductsArray.get(pos).getProductid();
					}
				});
	}

	private void btnSelectQualityClicked() {
		final String[] mQualitynames = { "Regular", "Premium" };
		new CommonUtils().SingleChoice("Select Quality", this, mQualitynames,
				meditSelectQuality, new SpinnerCallBack() {

					@Override
					public void onItemSelected(int pos) {
						meditSelectQuality.setText(mQualitynames[pos]);
					}
				});
	}

	private void btnSelectSizeClicked() {
		final String[] mSizeNames = { "16x30", "16x60" };
		new CommonUtils().SingleChoice("Select Size", this, mSizeNames,
				meditSelectSize, new SpinnerCallBack() {

					@Override
					public void onItemSelected(int pos) {
						meditSelectSize.setText(mSizeNames[pos]);
					}
				});
	}

	private void getLayoutReferences() {
		meditSelectColor = (TextView) findViewById(R.id.editSelectColor);
		meditSelectSize = (TextView) findViewById(R.id.editSelectSize);
		meditSelectQuality = (TextView) findViewById(R.id.editSelectQuality);
		meditBale = (EditText) findViewById(R.id.editBale);
		meditWeight = (EditText) findViewById(R.id.editWeight);
		meditQuantity = (EditText) findViewById(R.id.editQuantity);
		meditGsm = (EditText) findViewById(R.id.editGsm);
		meditPriceRetailer = (EditText) findViewById(R.id.editPriceRetailer);
		meditWholeSellerPrice = (EditText) findViewById(R.id.editWholeSellerPrice);

		StaticUtils.setEditTextHintFont(meditBale, this);
		StaticUtils.setEditTextHintFont(meditWeight, this);
		StaticUtils.setEditTextHintFont(meditQuantity, this);
		StaticUtils.setEditTextHintFont(meditPriceRetailer, this);
		StaticUtils.setEditTextHintFont(meditWholeSellerPrice, this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editSelectQuality:
			btnSelectQualityClicked();
			break;
		case R.id.editSelectColor:
			btnSelectColorClicked();
			break;
		case R.id.editSelectSize:
			btnSelectSizeClicked();
			break;
		}
	}

	private void initiateSupplierProductsWS() {
		showProgress();
		Bundle params = new Bundle();
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.SUPPLIER_PRODUCT_LIST, params),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseProductList(response);
					}

				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						hideProgress();
					}
				});

		RequestQueue mQueue = ((BaseApplication) getApplication()).getQueue();
		mQueue.add(mJsonRequestResponse);

	}

	private void responseProductList(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("0")) {
							showToast(mObj.getString(StaticUsage.MESSAGE));
						} else {
							loadAllData(response);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadAllData(JSONArray response) {
		mProductsArray = new ArrayList<MandapHolder>();
		mColors = new String[response.length()];
		for (int i = 0; i < response.length(); i++) {
			MandapHolder mDataHolder = new MandapHolder();
			try {
				JSONObject mObj = (JSONObject) response.get(i);
				if (mObj.has(StaticUsage.PRODUCT_ID)) {
					mDataHolder.setProductid(mObj
							.getString(StaticUsage.PRODUCT_ID));
				}
				if (mObj.has(StaticUsage.PRODUCT_NAME)) {
					mDataHolder.setProductName(mObj
							.getString(StaticUsage.PRODUCT_NAME));
				}
				mColors[i] = mObj.getString(StaticUsage.PRODUCT_NAME);
				mDataHolder.setProductChecked(false);
				mProductsArray.add(mDataHolder);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
}
