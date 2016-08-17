package com.mandap;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mandap.adapters.AdapterFavourites;
import com.mandap.adapters.AdapterProducts;
import com.utils.MandapHolder;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.UserDetails;
import com.utils.WSClass;

public class OrderActivity extends BaseActivity {

	private ArrayList<MandapHolder> mSuppliersArray;
	private ListView mListProducts;

	private EditText mEditSearch;
	private AdapterProducts mAdapterOrders;
	private String Keyword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_order);
		// setTopbarTitle(getString(R.string.suppliers));
		getLayoutReferences();
		setClickListeners();
		initiateSuppliersListWS();
	}

	private void setClickListeners() {
		mListProducts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	private void initiateSuppliersListWS() {
		showProgress();
		Bundle params = new Bundle();
		params.putString("user_id", String.valueOf(UserDetails.getInstance(
				OrderActivity.this).getUserId()));
		params.putString("keyword", Keyword);
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.SUPPLIER_LIST, params),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseSuppliersList(response);
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
		mListProducts = (ListView) findViewById(R.id.listSuppliers);
	}

	private void responseSuppliersList(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("0")) {
							showToast(mObj.getString(StaticUsage.MESSAGE));
							// mListSuppliers.setVisibility(View.INVISIBLE);
						} else {
							// mListSuppliers.setVisibility(View.VISIBLE);
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
		mSuppliersArray = new ArrayList<MandapHolder>();
		for (int i = 0; i < response.length(); i++) {
			MandapHolder mDataHolder = new MandapHolder();
			try {
				JSONObject mObj = (JSONObject) response.get(i);
				if (mObj.has(StaticUsage.SUPPLIER_ID)) {
					mDataHolder.setSupplierId(mObj
							.getString(StaticUsage.SUPPLIER_ID));
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
				mSuppliersArray.add(mDataHolder);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (mSuppliersArray != null && mSuppliersArray.size() > 0) {
			mAdapterOrders = new AdapterProducts(this);
			mAdapterOrders.addItems(mSuppliersArray);
			mListProducts.setAdapter(mAdapterOrders);
		}
	}
}
