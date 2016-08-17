package com.mandap;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mandap.adapters.AdapterSearch;
import com.mandap.interfaces.SearchCallbacks.ISearchCheck;
import com.utils.CommonUtils;
import com.utils.MandapHolder;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.WSClass;
import com.utils.CommonUtils.SpinnerCallBack;

public class SearchActivity extends BaseActivity implements OnClickListener,
		ISearchCheck {

	private ArrayList<MandapHolder> mProductsArray;

	private ImageView mbtnYes, mbtnNo;
	private TextView mbtnSearch, mbtnReset;
	private ListView mListViewProducts;
	private AdapterSearch mAdapterSearch;
	private String price, weight;
	private ArrayList<MandapHolder> mPriceList;
	private ArrayList<MandapHolder> mWeightList;

	private TextView mtxtSelectPrice, mtxtSelectQuality, mtxtSelectWeight,
			mtxtSelectSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_search);
		setTopbarTitle(getString(R.string.search));
		getLayoutReferences();
		setOnClickListeners();
		initiateProductListWS();
	}

	private void setOnClickListeners() {
		mbtnYes.setOnClickListener(this);
		mbtnNo.setOnClickListener(this);
		mbtnSearch.setOnClickListener(this);
		mbtnReset.setOnClickListener(this);
		mtxtSelectPrice.setOnClickListener(this);
		mtxtSelectQuality.setOnClickListener(this);
		mtxtSelectSize.setOnClickListener(this);
		mtxtSelectWeight.setOnClickListener(this);
	}

	private void initiateProductListWS() {
		showProgress();
		Bundle params = new Bundle();
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.PRODUCT_LIST_SEARCH_SCREEN,
						params), new Listener<JSONArray>() {

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

	private void getLayoutReferences() {
		mbtnYes = (ImageView) findViewById(R.id.yes);
		mbtnNo = (ImageView) findViewById(R.id.no);

		mbtnSearch = (TextView) findViewById(R.id.btnSearch);
		mbtnReset = (TextView) findViewById(R.id.btnReset);

		mListViewProducts = (ListView) findViewById(R.id.listProduct);
		mtxtSelectPrice = (TextView) findViewById(R.id.editSelectprice);
		mtxtSelectQuality = (TextView) findViewById(R.id.editSelectquality);
		mtxtSelectSize = (TextView) findViewById(R.id.editSelectsize);
		mtxtSelectWeight = (TextView) findViewById(R.id.editSelectweight);

		mbtnYes.setTag(1);
		mbtnNo.setTag(0);
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
				mDataHolder.setProductChecked(false);
				mProductsArray.add(mDataHolder);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		setListAdapter();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.yes:
			btnYesClicked();
			break;
		case R.id.no:
			btnNoClicked();
			break;
		case R.id.btnSearch:
			btnSearchClicked();
			break;
		case R.id.btnReset:
			btnResetClicked();
			break;
		case R.id.editSelectprice:
			initiatepriceClicked();
			break;

		case R.id.editSelectquality:
			btnselectqualityClicked();
			break;
		case R.id.editSelectweight:
			initiateweightClicked();
			break;
		case R.id.editSelectsize:
			btnselectsizeClicked();
			break;

		default:
			break;
		}
	}

	private void btnPriceClicked() {
		// if (mPriceList != null && mPriceList.size() != 0) {
		// final String[] mPriceDetails = new String[mPriceList.size()];
		// for (int i = 0; i < mPriceList.size(); i++) {
		// mPriceDetails[i] = mPriceList.get(i).getStateName();
		// }
		//
		// new CommonUtils().SingleChoice("Select Price", this, mPriceDetails,
		// mtxtSelectPrice, new SpinnerCallBack() {
		//
		// @Override
		// public void onItemSelected(int pos) {
		// mtxtSelectPrice.setText(mPriceDetails[pos]);
		// initiatepriceClicked();
		// }
		// });
		// }

	}

	private void btnselectsizeClicked() {
		final String[] mSizeNames = { "16x30", "16x60" };
		new CommonUtils().SingleChoice("Select Size", this, mSizeNames,
				mtxtSelectSize, new SpinnerCallBack() {

					@Override
					public void onItemSelected(int pos) {
						mtxtSelectSize.setText(mSizeNames[pos]);
					}
				});

	}

	private void initiateweightClicked() {
		showProgress();
		Bundle mParams = new Bundle();
		mParams.putString("loginusertype", "buyer");
		mParams.putString("type", "weight");
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.GETPRICEANDWEIGHT, mParams),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseforWeightClickedList(response);
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

	private void responseforWeightClickedList(JSONArray response) {
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

	private void btnselectqualityClicked() {

		final String[] mQualitynames = { "Regular", "Premium" };
		new CommonUtils().SingleChoice("Select Quality", this, mQualitynames,
				mtxtSelectQuality, new SpinnerCallBack() {

					@Override
					public void onItemSelected(int pos) {
						mtxtSelectQuality.setText(mQualitynames[pos]);
					}
				});

	}

	private void initiatepriceClicked() {
		showProgress();
		Bundle mParams = new Bundle();
		mParams.putString("loginusertype", "buyer");
		mParams.putString("type", "price");
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.GETPRICEANDWEIGHT, mParams),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseforWeightClickedList(response);
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

	private void btnResetClicked() {

	}

	private void btnSearchClicked() {
		
	// TODO-Roj
		Intent mIntent = new Intent(SearchActivity.this, SuppliersActivity.class);
		mIntent.putExtra("isFrom", "advanced_search");
		startActivity(mIntent);
		

	}

	private void btnYesClicked() {
		int mTagValue = (Integer) mbtnYes.getTag();
		if (mTagValue == 0) {
			mbtnYes.setTag(1);
			mbtnYes.setImageResource(R.drawable.btn_radio_h);
			mbtnNo.setTag(0);
			mbtnNo.setImageResource(R.drawable.btn_radio);
		} else {
		}
	}

	private void btnNoClicked() {
		int mTagValue = (Integer) mbtnNo.getTag();
		if (mTagValue == 0) {
			mbtnNo.setTag(1);
			mbtnNo.setImageResource(R.drawable.btn_radio_h);
			mbtnYes.setTag(0);
			mbtnYes.setImageResource(R.drawable.btn_radio);
		} else {
		}
	}

	private void setListAdapter() {
		mAdapterSearch = new AdapterSearch(this);
		mAdapterSearch.setCheckCallback((ISearchCheck) this);
		mAdapterSearch.addItems(mProductsArray);
		mListViewProducts.setAdapter(mAdapterSearch);
	}

	@Override
	public void onSearchClicked(int pos) {
		MandapHolder mHolder = mProductsArray.get(pos);
		if (mHolder.isProductChecked())
			mHolder.setProductChecked(false);
		else
			mHolder.setProductChecked(true);
		mProductsArray.set(pos, mHolder);
		mAdapterSearch.notifyDataSetChanged();
	}

}
