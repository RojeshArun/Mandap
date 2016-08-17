package com.mandap.suppliers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import com.mandap.BaseActivity;
import com.mandap.BaseApplication;
import com.mandap.R;
import com.mandap.SupplierDetailsActivity;
import com.mandap.adapters.AdapterSupplierProducts;
import com.utils.MandapHolder;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.UserDetails;
import com.utils.WSClass;

public class SupplierProductsActivity extends BaseActivity implements
		OnItemClickListener {

	private ArrayList<MandapHolder> mSuppliersArray;
	private ListView mListSuppliers;

	private AdapterSupplierProducts mAdapter;
	private String Keyword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_my_products);
		setTopbarTitle(getString(R.string.my_products));
		getLayoutReferences();
		setClickListeners();

	}

	@Override
	protected void onResume() {
		super.onResume();
		initiateSuppliersListWS();
	}

	private void setClickListeners() {
		mListSuppliers.setOnItemClickListener(this);
	}

	private void navigateToSupplierDetails(int position) {
		Intent mIntent = new Intent(this, ProductDetailsActivity.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("data", mSuppliersArray.get(position));
		mIntent.putExtras(mBundle);
		startActivity(mIntent);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
	}

	private void initiateSuppliersListWS() {
		showProgress();
		Bundle params = new Bundle();
		params.putString(
				"supplier_id",
				String.valueOf(UserDetails.getInstance(
						SupplierProductsActivity.this).getUserId()));
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.SUPPLIER_MY_PRODUCT, params),
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
				if (mObj.has(StaticUsage.SATTRIBUTE_ID)) {
					mDataHolder.setAttributeId(mObj
							.getString(StaticUsage.SATTRIBUTE_ID));
				}
				if (mObj.has(StaticUsage.PRODUCT_NAME)) {
					mDataHolder.setProductName(mObj
							.getString(StaticUsage.PRODUCT_NAME));
				}
				if (mObj.has(StaticUsage.PRODUCT_ID)) {
					mDataHolder.setProductid(mObj
							.getString(StaticUsage.PRODUCT_ID));
				}
				if (mObj.has(StaticUsage.SSEQ_ID)) {
					mDataHolder.setSequenceID(mObj
							.getString(StaticUsage.SSEQ_ID));
				}
				if (mObj.has(StaticUsage.SPRODUCT_ID)) {
					mDataHolder.setProductid(mObj
							.getString(StaticUsage.SPRODUCT_ID));
				}
				if (mObj.has(StaticUsage.SPRICE_SELLER)) {
					mDataHolder.setPriceSeller(mObj
							.getString(StaticUsage.SPRICE_SELLER));
				}
				if (mObj.has(StaticUsage.SQUANTITY)) {
					mDataHolder.setQuantity(mObj
							.getString(StaticUsage.SQUANTITY));
				}
				if (mObj.has(StaticUsage.SSIZE)) {
					mDataHolder.setSize(mObj.getString(StaticUsage.SSIZE));
				}
				if (mObj.has(StaticUsage.SWEIGHT)) {
					mDataHolder.setWeight(mObj.getString(StaticUsage.SWEIGHT));
				}
				if (mObj.has(StaticUsage.SBALE)) {
					mDataHolder.setBale(mObj.getString(StaticUsage.SBALE));
				}
				if (mObj.has(StaticUsage.SGSM)) {
					mDataHolder.setGsm(mObj.getString(StaticUsage.SGSM));
				}
				if (mObj.has(StaticUsage.SADDED_USER_ID)) {
					mDataHolder.setAddedUserId(mObj
							.getString(StaticUsage.SADDED_USER_ID));
				}
				if (mObj.has(StaticUsage.SPRICE_WHOLE_SELLER)) {
					mDataHolder.setPriceWholeSeller(mObj
							.getString(StaticUsage.SPRICE_WHOLE_SELLER));
				}
				if (mObj.has(StaticUsage.SDESCRIPTION)) {
					mDataHolder.setDescription(mObj
							.getString(StaticUsage.SDESCRIPTION));
				}
				if (mObj.has(StaticUsage.SVIRGIN_TYPE)) {
					mDataHolder.setVirginType(mObj
							.getString(StaticUsage.SVIRGIN_TYPE));
				}
				if (mObj.has(StaticUsage.SSTATUS)) {
					mDataHolder.setStatus(mObj.getString(StaticUsage.SSTATUS));
				}
				mSuppliersArray.add(mDataHolder);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (mSuppliersArray != null && mSuppliersArray.size() > 0) {
			mAdapter = new AdapterSupplierProducts(this);
			mAdapter.addItems(mSuppliersArray);
			mListSuppliers.setAdapter(mAdapter);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		navigateToSupplierDetails(position);
	}
}
