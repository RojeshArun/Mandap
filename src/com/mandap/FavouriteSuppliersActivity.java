package com.mandap;

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
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mandap.adapters.AdapterFavourites;
import com.utils.MandapHolder;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.UserDetails;
import com.utils.WSClass;

public class FavouriteSuppliersActivity extends BaseActivity implements
		OnItemClickListener {

	private ArrayList<MandapHolder> mFavouriteSuppliersList;

	private ListView mListSuppliers;
	private AdapterFavourites mAdapterFavourites;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_favourite_suppliers);
		setTopbarTitle(getString(R.string.favourites));
		getLayoutReferences();
		initiateFavouriteSuppliersListWS();
	}

	private void getLayoutReferences() {
		mListSuppliers = (ListView) findViewById(R.id.listSuppliers);
		mListSuppliers.setOnItemClickListener(this);
	}

	private void initiateFavouriteSuppliersListWS() {
		showProgress();

		Bundle params = new Bundle();
		params.putString(
				"user_id",
				String.valueOf(UserDetails.getInstance(
						FavouriteSuppliersActivity.this).getUserId()));
		params.putString("keyword", "");
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.FAVOURITE_SUPPLIER_LIST, params),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseFavouriteSuppliersList(response);
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

	private void responseFavouriteSuppliersList(JSONArray response) {
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
				if (mObj.has(StaticUsage.SUPPLIER_NAME)) {
					mDataHolder.setSupplierName(mObj
							.getString(StaticUsage.SUPPLIER_NAME));
				}
				if (mObj.has(StaticUsage.SUPPLIER_IMAGE)) {
					mDataHolder.setSupplierImage(mObj
							.getString(StaticUsage.SUPPLIER_IMAGE));
				}
				if (mObj.has(StaticUsage.USER_STATE_NAME)
						&& mObj.has(StaticUsage.ADDRESS)) {
					mDataHolder.setSupplierLocationName(mObj
							.getString(StaticUsage.ADDRESS)
							+ " "
							+ mObj.getString(StaticUsage.USER_STATE_NAME));
				}
				if (mObj.has(StaticUsage.DESCRIPTION)) {
					mDataHolder.setDescription(mObj
							.getString(StaticUsage.DESCRIPTION));
				}
				mDataHolder.setFavourite("Yes");
				mFavouriteSuppliersList.add(mDataHolder);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (mFavouriteSuppliersList != null
				&& mFavouriteSuppliersList.size() > 0) {
			mAdapterFavourites = new AdapterFavourites(this);
			mAdapterFavourites.addItems(mFavouriteSuppliersList);
			mListSuppliers.setAdapter(mAdapterFavourites);
		}
	}

	private void navigateToSupplierDetails(int position) {
		Intent mIntent = new Intent(this, SupplierDetailsActivity.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("data", mFavouriteSuppliersList.get(position));
		mIntent.putExtras(mBundle);
		startActivity(mIntent);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		navigateToSupplierDetails(position);
	}
}
