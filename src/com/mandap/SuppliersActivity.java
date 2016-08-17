package com.mandap;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import com.utils.MandapHolder;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.UserDetails;
import com.utils.WSClass;

public class SuppliersActivity extends BaseActivity implements
		OnItemClickListener {

	private ArrayList<MandapHolder> mSuppliersArray;
	private ListView mListSuppliers;

	private EditText mEditSearch;
	private AdapterFavourites mAdapterFavourites;
	private String Keyword;
	private String isFrom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_suppliers);
		setTopbarTitle(getString(R.string.suppliers));
		getLayoutReferences();
		setClickListeners();
		getData();
	}

	private void getData() {

		if (getIntent().getExtras() != null) {
			isFrom = getIntent().getExtras().get("isFrom").toString();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		initiateSuppliersListWS();
	}

	private void setClickListeners() {
		mEditSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (mAdapterFavourites != null)
					mAdapterFavourites.onTextChange("" + s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		mListSuppliers.setOnItemClickListener(this);
	}

	private void navigateToSupplierDetails(int position, String isFrom) {
		Intent mIntent = new Intent(this, SupplierDetailsActivity.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("data", mSuppliersArray.get(position));
		mBundle.putString("isFrom", isFrom);
		mIntent.putExtras(mBundle);
		startActivity(mIntent);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
	}

	private void initiateSuppliersListWS() {
		showProgress();
		Bundle params = new Bundle();
		params.putString("user_id", String.valueOf(UserDetails.getInstance(
				SuppliersActivity.this).getUserId()));
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
		mListSuppliers = (ListView) findViewById(R.id.listSuppliers);
		mEditSearch = (EditText) findViewById(R.id.editSearch);
		mEditSearch
				.setHintTextColor(getResources().getColor(R.color.dark_gray));
		StaticUtils.setEditTextHintFont(mEditSearch, this);
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
							mListSuppliers.setVisibility(View.INVISIBLE);
						} else {
							mListSuppliers.setVisibility(View.VISIBLE);
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
				if (mObj.has(StaticUsage.DESCRIPTION)) {
					mDataHolder.setDescription(mObj
							.getString(StaticUsage.DESCRIPTION));
				}
				if (mObj.has(StaticUsage.IS_FAV)) {
					mDataHolder
							.setFavourite(mObj.getString(StaticUsage.IS_FAV));
				}
				mSuppliersArray.add(mDataHolder);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (mSuppliersArray != null && mSuppliersArray.size() > 0) {
			mAdapterFavourites = new AdapterFavourites(this);
			mAdapterFavourites.addItems(mSuppliersArray);
			mListSuppliers.setAdapter(mAdapterFavourites);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mEditSearch.setText("");
		navigateToSupplierDetails(position,isFrom);
	}
}
