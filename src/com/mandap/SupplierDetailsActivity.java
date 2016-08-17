package com.mandap;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.androidquery.AQuery;
import com.mandap.adapters.AdapterProducts;
import com.utils.MandapHolder;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.UserDetails;
import com.utils.WSClass;

public class SupplierDetailsActivity extends BaseActivity implements
		OnClickListener {

	private ImageView mImageSupplier, mbtnFavourite;
	private TextView mtxtSupplierName, mtxtSupplierLocation, mbtnRequestQuote;
	private WebView mwebViewSupplierDesc;
	private ListView mListProducts;
	private MandapHolder mCurrentData;
	private AdapterProducts mAdapterProducts;

	private ArrayList<MandapHolder> mProductsArray;
	private String isFrom = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_supplier_detail);
		getLayoutReferences();
		setClickListeners();
		getData();

	}

	private void callSupplierDetailsWs() {
		// TODO Auto-generated method stub

		// http://mandapnet.com/api/consumer/my_requests_details?user_id=15&request_date=25%20Nov,%202014

		showProgress();

		Bundle params = new Bundle();
		params.putString(
				"user_id",
				String.valueOf(UserDetails.getInstance(
						SupplierDetailsActivity.this).getUserId()));
		params.putString("request_date", "25 Nov, 2014");// TODO

		JsonArrayRequest mJsonArrayRequest = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.MY_REQUEST_DETAILS, params),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						// TODO Auto-generated method stub
						hideProgress();

						parseSupliersData(response);

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						hideProgress();
					}
				});

		RequestQueue mQueue = ((BaseApplication) getApplication()).getQueue();
		mQueue.add(mJsonArrayRequest);

	}

	private void initiateSuppliersListWS() {
		showProgress();
		Bundle params = new Bundle();
		params.putString(
				"user_id",
				String.valueOf(UserDetails.getInstance(
						SupplierDetailsActivity.this).getUserId()));
		params.putString("supplier_id", mCurrentData.getSupplierId());
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.SUPPLIER_DETAILS, params),
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

	private void getData() {
		if (getIntent() != null && getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey("data")) {
				mCurrentData = (MandapHolder) getIntent().getExtras()
						.getSerializable("data");
				isFrom = getIntent().getExtras().getString("isFrom");
				setData();

				if (isFrom.equalsIgnoreCase("advanced_search")) {
					callSupplierDetailsWs();
//					initiateSuppliersListWS();
				} else {
					initiateSuppliersListWS();
				}

			}
		}
	}

	private void setData() {
		setTopbarTitle(mCurrentData.getSupplierName());
		mtxtSupplierName.setText(mCurrentData.getSupplierName());
		mtxtSupplierLocation.setText(mCurrentData.getSupplierLocationName());
		if (TextUtils.isEmpty(mCurrentData.getSupplierImage())
				|| mCurrentData.getSupplierImage().contains("noimage")) {

		} else {
			new AQuery(this).id(mImageSupplier).image(
					mCurrentData.getSupplierImage());
		}

		if (mCurrentData.getFavourite().equalsIgnoreCase("Yes")) {
			mbtnFavourite.setImageResource(R.drawable.icon_fav);
		} else {
			mbtnFavourite.setImageResource(R.drawable.icon_fav_h);
		}

		StaticUtils.loadHtmlContent(mwebViewSupplierDesc,
				mCurrentData.getDescription(), 14);
		mtxtSupplierName.setSelected(true);
		mtxtSupplierLocation.setSelected(true);
	}

	private void setClickListeners() {
		mbtnFavourite.setOnClickListener(this);
		mbtnRequestQuote.setOnClickListener(this);
	}

	private void getLayoutReferences() {
		mImageSupplier = (ImageView) findViewById(R.id.imgSupplier);
		mbtnFavourite = (ImageView) findViewById(R.id.imgFavourite);
		mtxtSupplierName = (TextView) findViewById(R.id.txtSupplierName);
		mtxtSupplierLocation = (TextView) findViewById(R.id.txtLocation);
		mbtnRequestQuote = (TextView) findViewById(R.id.btnRequestQuoute);
		mwebViewSupplierDesc = (WebView) findViewById(R.id.webViewSupplierInfo);
		mListProducts = (ListView) findViewById(R.id.listProducts);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgFavourite:
			btnFavouriteClicked();
			break;
		case R.id.btnRequestQuoute:
			btnRequestQuoteClicked();
			break;
		default:
			break;
		}
	}

	private void btnRequestQuoteClicked() {
		navigateToRequestQuote();
	}

	private void navigateToRequestQuote() {
		Intent mIntent = new Intent(this, RequestQuoteActivity.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("data", mCurrentData);
		mIntent.putExtras(mBundle);
		startActivity(mIntent);
	}

	private void btnFavouriteClicked() {
		if (mCurrentData.getFavourite().equalsIgnoreCase("Yes")) {
			mbtnFavourite.setImageResource(R.drawable.icon_fav_h);
			initiateFavouriteWS("No");
		} else {
			mbtnFavourite.setImageResource(R.drawable.icon_fav);
			initiateFavouriteWS("Yes");
		}
	}

	private void initiateFavouriteWS(String string) {
		showProgress();
		Bundle params = new Bundle();
		params.putString("user_id",
				String.valueOf(UserDetails.getInstance(this).getUserId()));
		params.putString("supplier_id", mCurrentData.getSupplierId());
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.ADD_FAV_SUPPLIER, params),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						reponseFavouriteListWS(response);
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

	private void reponseFavouriteListWS(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("0")) {
							showToast(mObj.getString(StaticUsage.MESSAGE));
						} else {
							if (mCurrentData.getFavourite().equalsIgnoreCase(
									"Yes")) {
								mCurrentData.setFavourite("No");
								mbtnFavourite
										.setImageResource(R.drawable.icon_fav_h);
							} else {
								mCurrentData.setFavourite("Yes");
								mbtnFavourite
										.setImageResource(R.drawable.icon_fav);
							}
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

	private void setListAdpater(ArrayList<MandapHolder> mArrayList) {
		mAdapterProducts = new AdapterProducts(this);
		mAdapterProducts.addItems(mArrayList);
		mListProducts.setAdapter(mAdapterProducts);
	}

	private void responseSuppliersList(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("1")) {
							showSupplierInformation(response);
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

	protected void parseSupliersData(JSONArray response) {

		if (response != null && response.length() != 0) {
			setAdvancedSupplierProducts(response);
		}

	}

	private void showSupplierInformation(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("0")) {
							showToast(mObj.getString(StaticUsage.MESSAGE));
						} else {
							loadSupplierInfo(response);
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

	private void loadSupplierInfo(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mCurrentData = response.getJSONObject(0);
				mtxtSupplierName.setText(mCurrentData
						.getString(StaticUsage.SUPPLIER_NAME));
				mtxtSupplierLocation.setText(mCurrentData
						.getString(StaticUsage.ADDRESS));
				if (TextUtils.isEmpty(mCurrentData
						.getString(StaticUsage.SUPPLIER_IMAGE))
						|| mCurrentData.getString(StaticUsage.SUPPLIER_IMAGE)
								.contains("noimage")) {

				} else {
					new AQuery(this).id(mImageSupplier).image(
							mCurrentData.getString(StaticUsage.SUPPLIER_IMAGE));
				}

				if (mCurrentData.getString(StaticUsage.IS_FAV)
						.equalsIgnoreCase("Yes")) {
					mbtnFavourite.setImageResource(R.drawable.icon_fav);
				} else {
					mbtnFavourite.setImageResource(R.drawable.icon_fav_h);
				}

				StaticUtils.loadHtmlContent(mwebViewSupplierDesc,
						mCurrentData.getString(StaticUsage.DESCRIPTION), 14);
				mtxtSupplierName.setSelected(true);
				mtxtSupplierLocation.setSelected(true);
				if (mCurrentData.has(StaticUsage.PRODUCT_LIST)) {
					JSONArray mProductsListArray = mCurrentData
							.getJSONArray(StaticUsage.PRODUCT_LIST);
					setSupplierProducts(mProductsListArray);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

	private void setAdvancedSupplierProducts(JSONArray mProductsListArray) {
		ArrayList<MandapHolder> mArrayList = new ArrayList<MandapHolder>();
		if (mProductsListArray != null && mProductsListArray.length() != 0) {
			for (int i = 0; i < mProductsListArray.length(); i++) {
				try {

					JSONObject mObj = mProductsListArray.getJSONObject(i);
					MandapHolder mHolder = new MandapHolder();
					mHolder.setProductName(mObj
							.getString(StaticUsage.PRODUCT_NAME));
					mHolder.setQuantity(mObj.getString(StaticUsage.QUANTITY));
					mHolder.setPrice(mObj.getString(StaticUsage.PRODUCT_PRICE));
					mArrayList.add(mHolder);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		if (mArrayList != null)
			setListAdpater(mArrayList);

	}

	private void setSupplierProducts(JSONArray mProductsListArray) {
		ArrayList<MandapHolder> mArrayList = new ArrayList<MandapHolder>();
		if (mProductsListArray != null && mProductsListArray.length() != 0) {
			for (int i = 0; i < mProductsListArray.length(); i++) {
				try {

					JSONObject mObj = mProductsListArray.getJSONObject(i);
					MandapHolder mHolder = new MandapHolder();
					mHolder.setProductName(mObj
							.getString(StaticUsage.PRODUCT_NAME));
					mHolder.setQuantity(mObj.getString(StaticUsage.STOCK));
					mHolder.setPrice(mObj.getString(StaticUsage.SPRICE_SELLER));
					mArrayList.add(mHolder);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		if (mArrayList != null)
			setListAdpater(mArrayList);

	}
}
