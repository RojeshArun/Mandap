package com.mandap;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mandap.adapters.AdapterMyRequests;
import com.utils.MandapHolder;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.UserDetails;
import com.utils.WSClass;

public class MyRequestsActivity extends BaseActivity {

	private ArrayList<MandapHolder> mFavouriteSuppliersList;
	private ListView mListSuppliers;
	private AdapterMyRequests mAdapterMyRequests;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_favourite_suppliers);
		setTopbarTitle(getString(R.string.my_requests));
		getLayoutReferences();
		setClickListeners();
		initiateMyRequestsWS();
	}

	private void setClickListeners() {
		mListSuppliers.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
//				goto

			}
		});
	}

	// private void setListAdapter() {
	// mAdapterMyRequests = new AdapterMyRequests(this);
	// mAdapterMyRequests.addItems(getDummyData());
	// mListSuppliers.setAdapter(mAdapterMyRequests);
	// }
	//
	// private ArrayList<MandapHolder> getDummyData() {
	// ArrayList<MandapHolder> mArray = new ArrayList<MandapHolder>();
	// for (int i = 0; i < 10; i++) {
	// MandapHolder mHolder = new MandapHolder();
	// mHolder.setSupplierName("Supplier Name " + (i + 1));
	// mHolder.setRequestDate("" + (i + 1) + " Oct, 2014");
	// mHolder.setSupplierImage("http://www.hdnicewallpapers.com/Walls/Big/Bikes/Amazing_Bike_Stunt_Images.jpg");
	// mArray.add(mHolder);
	// }
	// return mArray;
	// }

	private void getLayoutReferences() {
		mListSuppliers = (ListView) findViewById(R.id.listSuppliers);
	}

	private void initiateMyRequestsWS() {
		showProgress();

		Bundle params = new Bundle();
		params.putString("user_id", String.valueOf(UserDetails.getInstance(
				MyRequestsActivity.this).getUserId()));
		params.putString("keyword", "");
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.MY_REQUESTS, params),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseMyRequestsList(response);
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

	private void responseMyRequestsList(JSONArray response) {
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
		mFavouriteSuppliersList = new ArrayList<MandapHolder>();
		for (int i = 0; i < response.length(); i++) {
			MandapHolder mDataHolder = new MandapHolder();
			try {
				JSONObject mObj = (JSONObject) response.get(i);
				if (mObj.has(StaticUsage.SUPPLIER_ID)) {
					mDataHolder.setSupplierId(mObj
							.getString(StaticUsage.SUPPLIER_ID));
				}
				if (mObj.has(StaticUsage.QUOTE_ID)) {
					mDataHolder
							.setQuoteId(mObj.getString(StaticUsage.QUOTE_ID));
				}
				if (mObj.has(StaticUsage.SUPPLIER_NAME)) {
					mDataHolder.setSupplierName(mObj
							.getString(StaticUsage.SUPPLIER_NAME));
				}
				if (mObj.has(StaticUsage.SUPPLIER_IMAGE)) {
					mDataHolder.setSupplierImage(mObj
							.getString(StaticUsage.SUPPLIER_IMAGE));
				}
				if (mObj.has(StaticUsage.CITY_NAME)) {
					mDataHolder.setSupplierLocationName(mObj
							.getString(StaticUsage.CITY_NAME));
				}
				if (mObj.has(StaticUsage.REQUESTED_DATE)) {
					mDataHolder.setRequestDate(mObj
							.getString(StaticUsage.REQUESTED_DATE));
				}

				mFavouriteSuppliersList.add(mDataHolder);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (mFavouriteSuppliersList != null
				&& mFavouriteSuppliersList.size() > 0) {
			mAdapterMyRequests = new AdapterMyRequests(this);
			mAdapterMyRequests.addItems(mFavouriteSuppliersList);

			mListSuppliers.setAdapter(mAdapterMyRequests);
		}
	}
}
